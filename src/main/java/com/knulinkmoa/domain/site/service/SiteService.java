package com.knulinkmoa.domain.site.service;


import com.knulinkmoa.domain.directory.entity.Directory;
import com.knulinkmoa.domain.site.dto.request.SiteSaveRequest;
import com.knulinkmoa.domain.site.dto.request.SiteUpdateRequset;
import com.knulinkmoa.domain.site.dto.response.SiteReadResponse;
import com.knulinkmoa.domain.site.entity.Site;
import com.knulinkmoa.domain.site.exception.SiteErrorCode;
import com.knulinkmoa.domain.site.repository.SiteRepository;
import com.knulinkmoa.global.exception.GlobalException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SiteService {

    private final SiteRepository siteRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long saveSite(SiteSaveRequest request){
        Site site = Site.builder()
                .id(request.id())
                .url(request.url())
                .name(request.name())
                .directory(request.directory())
                .build();
        siteRepository.save(site);

        return site.getId();
    }

    /**
     * READ
     */

    public SiteReadResponse readSite(Long siteId){
        Site site=siteRepository.findById(siteId)
                .orElseThrow(() -> new GlobalException(SiteErrorCode.SITE_NOT_FOUND));

        return SiteReadResponse.builder()
                .id(site.getId())
                .name(site.getName())
                .directory(site.getDirectory())
                .build();
    }

    /**
     * UPDATE
     */

    @Transactional
    public Long updateSite(SiteUpdateRequset requsetBody,Long oldSiteId){
        //site ID 조회 => 해당되는 데이터 선택  => 예외 처리 => 업데이트 => 저장
        Site site = siteRepository.findById(oldSiteId)
                .orElseThrow(() -> new GlobalException((SiteErrorCode.SITE_NOT_FOUND)));

        site.update(requsetBody);

        siteRepository.save(site);
        return site.getId();
    }

    /**
     * DELETE
     */
    @Transactional
    public void deleteSite(Long siteId){
        Site deleteSite = siteRepository.findById(siteId)
                .orElseThrow(() -> new GlobalException(SiteErrorCode.SITE_NOT_FOUND));
        siteRepository.delete(deleteSite);
    }
}
