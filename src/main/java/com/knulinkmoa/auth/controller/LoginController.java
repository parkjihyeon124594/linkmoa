/*
package com.knulinkmoa.auth.controller;

import com.knulinkmoa.auth.dto.request.LoginRequest;
import com.knulinkmoa.auth.dto.response.LoginResponse;
import com.knulinkmoa.auth.service.AuthService;
import com.knulinkmoa.global.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiUtil.ApiSuccessResult<LoginResponse>> login(
            @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.googleLogin(request);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, loginResponse));
    }

}
*/
