package com.knulinkmoa.auth.handler;

import com.knulinkmoa.global.details.PrincipalDetails;
import com.knulinkmoa.global.jwt.provider.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //일반적으로 OAuth2 인증 프로토콜에서는 사용자의 속성 정보를 JSON 형태로 제공
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) auth;


        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        String accessToken = jwtTokenProvider.createJwt("accessToekn",oAuth2User.getEmail(), role,  600000L); // 10분
        String refreshToken = jwtTokenProvider.createJwt("refreshToekn", oAuth2User.getEmail(), role,86400000L); // 1일

        log.info("oauth2 login accessToken : {}",accessToken);
        log.info("oauth2 login refreshToken : {}",refreshToken);

        response.setHeader("accessToken",accessToken);
        response.addCookie(jwtTokenProvider.createCookie("Authorization",refreshToken));
        response.sendRedirect("http://localhost:3000");

    }


}
