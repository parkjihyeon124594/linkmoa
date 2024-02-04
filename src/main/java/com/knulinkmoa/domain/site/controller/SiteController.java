package com.knulinkmoa.domain.site.controller;

import com.knulinkmoa.domain.global.util.ApiUtil;
import com.knulinkmoa.domain.member.dto.request.MemberSaveRequest;
import com.knulinkmoa.domain.site.dto.request.SiteSaveRequest;
import com.knulinkmoa.domain.site.repository.SiteRepository;
import com.knulinkmoa.domain.site.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    /**
     * 사이트 추가
     */
    @PostMapping
    private ResponseEntity<ApiUtil.ApiSuccessResult<Long>> save(
            @RequestBody SiteSaveRequest request){
        Long saveSiteId=siteService.saveSite(request);
        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK,saveSiteId));
    }
    
}
