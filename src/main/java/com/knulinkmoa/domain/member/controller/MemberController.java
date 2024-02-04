package com.knulinkmoa.domain.member.controller;

import com.knulinkmoa.domain.global.util.ApiUtil;
import com.knulinkmoa.domain.member.dto.request.MemberSaveRequest;
import com.knulinkmoa.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    private ResponseEntity<ApiUtil.ApiSuccessResult<Long>> save(
            @RequestBody MemberSaveRequest request) {

        Long saveMemberId = memberService.saveMember(request);
        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, saveMemberId));
    }


}
