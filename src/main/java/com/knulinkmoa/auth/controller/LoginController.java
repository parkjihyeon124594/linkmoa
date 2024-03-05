package com.knulinkmoa.auth.controller;

import com.knulinkmoa.auth.service.signupService;
import com.knulinkmoa.domain.member.dto.request.MemberSignUpDTO;
import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.member.reposotiry.MemberRepository;
import com.knulinkmoa.domain.member.service.MemberService;
import com.knulinkmoa.global.details.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final signupService signupService;

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> googleLogin(
            @AuthenticationPrincipal PrincipalDetails principalDetails
            ) {
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(
            @RequestBody MemberSignUpDTO memberSignUpDTO
    ){
        signupService.signupMember(memberSignUpDTO);

        return ResponseEntity.ok("회원가입 성공");
    }


}
