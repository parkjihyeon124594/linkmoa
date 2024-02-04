package com.knulinkmoa.domain.directory.controller;

import com.knulinkmoa.domain.directory.dto.request.SaveRequest;
import com.knulinkmoa.domain.directory.dto.response.ReadResponse;
import com.knulinkmoa.domain.directory.service.DirectoryService;
import com.knulinkmoa.domain.global.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private ResponseEntity<ApiUtil.ApiSuccessResult<Long>> save(
            @RequestBody SaveRequest request,
            @PathVariable("directoryId") Long id
    ) {
        if ("directory".equals(request.type())) {
            Long saveDirectoryId = directoryService.saveDirectory(request, id);
            return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.CREATED, saveDirectoryId));
        } else if ("site".equals(request.type())) {
            Long saveSiteId = directoryService.saveSite(request, id);
            return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.CREATED, saveSiteId));
        }else {
            return ResponseEntity.badRequest().body(ApiUtil.success(HttpStatus.BAD_REQUEST));
        }
    }

    /**
     * READ
     */
    @GetMapping("/{directoryId}")
    private ResponseEntity<ApiUtil.ApiSuccessResult<ReadResponse>> read(
            @PathVariable("directoryId") Long id
    ) {

        ReadResponse response = directoryService.readDirectory(id);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, response));
    }

}
