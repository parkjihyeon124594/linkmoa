package com.knulinkmoa.domain.site.controller;


import com.knulinkmoa.domain.global.util.ApiUtil;
import com.knulinkmoa.domain.site.dto.request.SiteSaveRequest;
import com.knulinkmoa.domain.site.dto.request.SiteUpdateRequest;
import com.knulinkmoa.domain.site.dto.response.SiteReadResponse;
import com.knulinkmoa.domain.site.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dir/{directoryId}")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    /**
     * 사이트 추가(CREATE)
     * @param request 사이트 추가 DTO
     * @param directoryId 디렉토리 ID (PK)
     * @return 추가한 데이터 PK값
     */
    @PostMapping()
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> save(
            @RequestBody SiteSaveRequest request,
            @PathVariable("directoryId") Long id
            )
    {
        Long saveSiteId=siteService.saveSite(request, id);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.CREATED, saveSiteId));
    }

    /**
     * 사이트 정보 조회 (READ)
     * @param siteId 조회 DTO
     * @return 사이트 정보
     */
    @GetMapping("/sites/{siteId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<SiteReadResponse>>read
        ( @PathVariable("siteId") Long siteId)
    {
        SiteReadResponse response=siteService.readSite(siteId);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, response));
    }


    /**
     * 사이트 정보 수정 (UPDATE)
     * @param request 사이트 수정 DTO
     * @return 수정한 데이터 PK값
     */

    @PutMapping("/sites/{siteId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> update(
            @RequestBody SiteUpdateRequest request,
            @PathVariable("siteId") Long oldSiteId
            )
    {
        Long newSiteid=siteService.updateSite(request,oldSiteId);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.CREATED, newSiteid));
    }
    /**
     * DELETE
     * @param siteId 조회 조회 DTO
     * @return
     */

    @DeleteMapping("/sites/{siteId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<?>> delete(
            @PathVariable("siteId") Long siteId
            )
    {
        siteService.deleteSite(siteId);
        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK));
    }
}
