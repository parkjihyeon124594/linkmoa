package com.knulinkmoa.global.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

    private final SecretKey secretKey;

    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getEmail(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);

            System.out.println(" = " + "========== 벨리데이트 테스트 =========") ;

            JwsHeader header = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getHeader();
            for (String s : header.keySet()) {
                System.out.println("header = " + header);
            }
            for (Object value : header.values()) {
                System.out.println("value = " + value);

            }
            Claims payload = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            for (Map.Entry<String, Object> stringObjectEntry : payload.entrySet()) {
                System.out.println("payload = " + stringObjectEntry);
            }

            System.out.println(" = " + "========== 벨리데이트 테스트 끝 =========") ;

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    public String createJwt(String email, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String bearerTokenResolver(String bearerToken) {
        if (StringUtils.startsWithIgnoreCase(bearerToken, "Bearer ")) {
            return bearerToken.replace("Bearer", "").trim();
        }

        return null;
    }
}