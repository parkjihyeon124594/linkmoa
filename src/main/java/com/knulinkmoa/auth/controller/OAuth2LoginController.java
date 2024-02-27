package com.knulinkmoa.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class OAuth2LoginController {

    @GetMapping("/login")
    public ResponseEntity<String> googleLogin(
            @CookieValue(value = "Authorization") String bearerToken
    ) {

        System.out.println("bearerToken = " + bearerToken);

        return ResponseEntity.ok().body("ok");
    }

}
