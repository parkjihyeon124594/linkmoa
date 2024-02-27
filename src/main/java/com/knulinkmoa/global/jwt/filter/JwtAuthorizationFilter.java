package com.knulinkmoa.global.jwt.filter;

import com.knulinkmoa.auth.dto.request.OAuth2DTO;
import com.knulinkmoa.auth.service.CustomOAuth2User;
import com.knulinkmoa.global.jwt.provider.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = Arrays.stream(request.getCookies())
                .filter(header -> header.getName().equals("Authorization"))
                .findAny()
                .map(cookie -> cookie.getValue())
                .orElse(null);

        String token = jwtTokenProvider.bearerTokenResolver(bearerToken);

        System.out.println("token = " + token);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getEmail(token);
            String role = jwtTokenProvider.getRole(token);

            OAuth2DTO oAuth2DTO = OAuth2DTO.builder()
                    .email(email)
                    .role(role)
                    .build();

            //UserDetails에 회원 정보 객체 담기
            CustomOAuth2User customOAuth2User = new CustomOAuth2User(oAuth2DTO);

            //스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
            //세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }
        filterChain.doFilter(request, response);
    }
}

