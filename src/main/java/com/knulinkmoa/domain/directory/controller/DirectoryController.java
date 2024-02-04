package com.knulinkmoa.domain.directory.controller;

import com.knulinkmoa.domain.directory.dto.request.DeleteRequest;
import com.knulinkmoa.domain.directory.dto.request.SaveRequest;
import com.knulinkmoa.domain.directory.dto.request.UpdateRequest;
import com.knulinkmoa.domain.directory.dto.response.ReadResponse;
import com.knulinkmoa.domain.directory.service.DirectoryService;
import com.knulinkmoa.domain.global.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/directory")
@RequiredArgsConstructor
public class DirectoryController {

    private final DirectoryService directoryService;

    /**
     * CREATE
     */
    @PostMapping("/{directoryId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> save(
            @RequestBody SaveRequest request,
            @PathVariable("directoryId") Long id
    ) {
        if ("directory".equals(request.type())) {
            Long saveDirectoryId = directoryService.saveDirectory(request, id);
            return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.CREATED, saveDirectoryId));
        } else if ("site".equals(request.type())) {
            Long saveSiteId = directoryService.saveSite(request, id);
            return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.CREATED, saveSiteId));
        } else {
            return ResponseEntity.badRequest().body(ApiUtil.success(HttpStatus.BAD_REQUEST));
        }
    }

    /**
     * READ
     */
    @GetMapping("/{directoryId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<ReadResponse>> read(
            @PathVariable("directoryId") Long id
    ) {
        ReadResponse response = directoryService.readDirectory(id);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, response));
    }

    /**
     * UPDATE
     */
    @PutMapping("/{directoryId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> updateDirectory(
            @RequestBody UpdateRequest request,
            @PathVariable("directoryId") Long id
    ) {

        if ("directory".equals(request.type())) {
            Long updateDirectoryId = directoryService.updateDirectory(request, id);
            return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, updateDirectoryId));
        } else if ("site".equals(request.type())) {
            Long updateSiteId = directoryService.updateSite(request);
            return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, id));
        } else {
            return ResponseEntity.badRequest().body(ApiUtil.success(HttpStatus.BAD_REQUEST));
        }
    }

    /**
     * DELETE
     */
    @DeleteMapping("/{directoryId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<?>> deleteDirectory(
            @RequestBody DeleteRequest request,
            @PathVariable("directoryId") Long id) {

        if ("directory".equals(request.type())) {
            directoryService.deleteDirectory(id);
            return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK));
        } else if ("site".equals(request.type())) {
            directoryService.deleteSite(request.siteId());
            return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK));
        } else {
            return ResponseEntity.badRequest().body(ApiUtil.success(HttpStatus.BAD_REQUEST));
        }
    }

}