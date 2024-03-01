package com.knulinkmoa.global.config;

import com.knulinkmoa.auth.handler.CustomOAuth2SuccessHandler;
import com.knulinkmoa.auth.service.CustomOAuth2UserService;
import com.knulinkmoa.domain.member.service.MemberService;
import com.knulinkmoa.global.jwt.filter.JwtAuthorizationFilter;
import com.knulinkmoa.global.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return
                http
                        .csrf(AbstractHttpConfigurer::disable)
                        .formLogin(AbstractHttpConfigurer::disable)
                        .httpBasic(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests((auth) -> auth
                                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                                .anyRequest().authenticated())
                        .addFilterBefore(new JwtAuthorizationFilter(memberService, jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                        .oauth2Login(oauth2 -> oauth2
                                        .userInfoEndpoint(userInfoEndpointConfig ->
                                                userInfoEndpointConfig.userService(customOAuth2UserService))
                                        .successHandler(customOAuth2SuccessHandler)
                                )
                        .sessionManagement((session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .build();
    }

}
