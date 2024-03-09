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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        String accessToken =jwtTokenProvider.createJwt("accessToken",oAuth2User.getEmail(),role,600000L);
        String refreshToken =jwtTokenProvider.createJwt("refreshToken", oAuth2User.getEmail(), role,86400000L);

        log.info("custom login access token : {}",accessToken);
        log.info("custom login refresh token : {}",refreshToken);
        /*각각의 토큰은 생명주기와 사용처가 다르기 때문에 2강에서 설명드린바와 같이 서로 다른 저장소에 발급합니다.

                - Access : 헤더에 발급 후 프론트에서 로컬 스토리지 저장
                - Refresh : 쿠키에 발급

         */
        response.setHeader("accessToken",accessToken);
        response.addCookie(jwtTokenProvider.createCookie("Authorization",refreshToken));
        response.sendRedirect("http://localhost:3000");


    }
}
