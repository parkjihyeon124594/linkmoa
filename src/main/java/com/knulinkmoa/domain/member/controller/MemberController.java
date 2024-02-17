package com.knulinkmoa.domain.member.controller;

import com.knulinkmoa.domain.member.dto.request.MemberSignUpDTO;
import com.knulinkmoa.domain.member.service.MemberService;
import com.knulinkmoa.global.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> saveMember(
            @RequestBody MemberSignUpDTO request)
    {
        Long savedId = memberService.saveMember(request);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, savedId));
    }
}
