package com.knulinkmoa.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knulinkmoa.auth.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.knulinkmoa.auth.handler.CustomFailureHandler;
import com.knulinkmoa.auth.handler.CustomLoginSuccessHandler;
import com.knulinkmoa.auth.handler.CustomOAuth2SuccessHandler;
import com.knulinkmoa.domain.member.service.MemberService;
import com.knulinkmoa.global.details.PrincipalDetailsService;
import com.knulinkmoa.global.jwt.filter.JwtAuthorizationFilter;
import com.knulinkmoa.global.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final PrincipalDetailsService principalDetailsService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoderConfig passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return
                http
                        .csrf(AbstractHttpConfigurer::disable)
                        .formLogin(AbstractHttpConfigurer::disable)
                        .httpBasic(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests((auth) -> auth
                                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                                .anyRequest().authenticated())
                        .addFilterBefore(new JwtAuthorizationFilter(memberService, jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                        .addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class)
                        .oauth2Login(oauth2 -> oauth2
                                        .userInfoEndpoint(userInfoEndpointConfig ->
                                                userInfoEndpointConfig.userService(principalDetailsService))
                                        .successHandler(customOAuth2SuccessHandler)
                                )
                        .sessionManagement((session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder.passwordEncoder());
        provider.setUserDetailsService(principalDetailsService);

        return new ProviderManager(provider);
    }

    @Bean
    public CustomLoginSuccessHandler loginSuccessHandler() {
        return new CustomLoginSuccessHandler(jwtTokenProvider);
    }

    @Bean
    public CustomFailureHandler loginFailureHandler() {
        return new CustomFailureHandler();
    }
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);

        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());

        return customJsonUsernamePasswordAuthenticationFilter;
    }

}
