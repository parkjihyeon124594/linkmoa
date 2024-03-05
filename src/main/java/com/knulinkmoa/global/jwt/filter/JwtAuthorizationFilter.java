package com.knulinkmoa.global.jwt.filter;

import com.knulinkmoa.domain.member.service.MemberService;
import com.knulinkmoa.global.details.PrincipalDetails;
import com.knulinkmoa.global.jwt.provider.JwtTokenProvider;
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

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String [] excludePathLists = {"/login", "/favicon.ico","/auth/sign-up",};
        String path = request.getRequestURI();

        return Arrays.stream(excludePathLists).
                anyMatch((excludePath) -> excludePath.equals(path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromCookie(request.getCookies());
        // String token = jwtTokenProvider.bearerTokenResolver(bearerToken);

        if (token != null && jwtTokenProvider.validateToken(token)) {

            String email = jwtTokenProvider.getEmail(token);

            //UserDetails에 회원 정보 객체 담기

            PrincipalDetails principalDetails =new PrincipalDetails(memberService.findMemberByEmail(email));
            //스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            //SecurityContextHolder에 사용자 정보 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private static String getTokenFromCookie(Cookie[] cookies) {
        String token = null;

        if (cookies == null) {
            return token;
        } else {
            token = Arrays.stream(cookies)
                    .filter(header -> header.getName().equals("Authorization"))
                    .findAny()
                    .map(cookie -> cookie.getValue())
                    .orElse(null);

            return token;
        }
    }
}

