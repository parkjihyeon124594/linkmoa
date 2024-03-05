package com.knulinkmoa.auth.handler;

import com.knulinkmoa.global.details.PrincipalDetails;
import com.knulinkmoa.global.jwt.provider.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails oAuth2User = (PrincipalDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        String token = jwtTokenProvider.createJwt(oAuth2User.getEmail(), role, 60 * 60 * 60L);

        response.addCookie(jwtTokenProvider.createCookie("Authorization",token));
        response.sendRedirect("http://localhost:3000");
    }


}
