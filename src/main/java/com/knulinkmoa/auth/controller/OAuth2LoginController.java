package com.knulinkmoa.auth.controller;

import com.knulinkmoa.auth.service.CustomOAuth2User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class OAuth2LoginController {

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> googleLogin(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
            ) {


        return ResponseEntity.ok().body("ok");
    }

}
