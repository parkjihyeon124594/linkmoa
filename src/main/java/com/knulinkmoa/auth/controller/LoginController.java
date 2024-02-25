package com.knulinkmoa.auth.controller;

import com.knulinkmoa.auth.dto.request.LoginRequest;
import com.knulinkmoa.auth.dto.response.LoginResponse;
import com.knulinkmoa.auth.service.AuthService;
import com.knulinkmoa.global.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    @GetMapping
    public String mainPage() {

        return "success";
    }

    @RequestMapping("/oauth2/code/google")
    public ResponseEntity<ApiUtil.ApiSuccessResult<LoginResponse>> login(
            @RequestParam("code") String authcode
    ) {

        System.out.println("authcode = " + authcode);
        LoginResponse loginResponse = authService.googleLogin(authcode);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, loginResponse));
    }

}
