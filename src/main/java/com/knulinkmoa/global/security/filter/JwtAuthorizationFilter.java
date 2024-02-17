package com.knulinkmoa.global.security.filter;

import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.global.security.details.CustomUserDetails;
import com.knulinkmoa.global.security.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // JWT 토큰을 가져옴
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            System.out.println("token is null");
            filterChain.doFilter(request, response);

            return;
        }

        System.out.println("===============================");
        System.out.println("authorization now");

        //Bearer 부분 제거 후 순수 토큰만 획득
        token = token.replace("Bearer", "").trim();

        System.out.println("검증시 token = " + token);


       if (jwtTokenProvider.isExpired(token)) {
            System.out.println("token이 만료되었습니다");
            filterChain.doFilter(request, response);

            return;
        }

        String username = jwtTokenProvider.getUserName(token);
        String role = jwtTokenProvider.getRole(token);

        System.out.println("검증시 username = " + username);
        System.out.println("검증시 role = " + role);

        Member member = Member.builder()
                .username(username)
                .role(role)
                .password("aaaaaa")
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        Authentication auth = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
        System.out.println("authorization 끝");
    }
}
