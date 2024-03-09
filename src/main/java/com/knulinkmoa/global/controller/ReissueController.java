package com.knulinkmoa.global.controller;

import com.knulinkmoa.global.jwt.provider.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class ReissueController {

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response){

        //get refresh token
        String refreshToken = null;
        Cookie[] cookies =request.getCookies();
        for(Cookie cookie : cookies){

            if(cookie.getName().equals("refreshToken")){
                refreshToken = cookie.getValue();
            }
        }

        if(refreshToken == null){

            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        //expired check

        try{
            jwtTokenProvider.isExpired(refreshToken);
        }catch (ExpiredJwtException e){

            //response staus code
            return new ResponseEntity<>("refresh token expired",HttpStatus.BAD_REQUEST);
        }

        //토큰이 리프레시 토큰인지 확인 (발급 시 페이로드에 명시함)
        String category = jwtTokenProvider.getCategory(refreshToken);

        if(!category.equals("refreshToken")){

            //response status code
            return new ResponseEntity<>("invalid refresh token ",HttpStatus.BAD_REQUEST);

        }

        String email = jwtTokenProvider.getEmail(refreshToken);
        String role = jwtTokenProvider.getRole(refreshToken);

        String newAccessToken = jwtTokenProvider.createJwt("accessToken",email,role,60*60*60L);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
