package com.knulinkmoa.domain.directory.service;

import com.knulinkmoa.domain.directory.dto.request.SaveRequest;
import com.knulinkmoa.domain.directory.dto.request.UpdateRequest;
import com.knulinkmoa.domain.directory.dto.response.ReadResponse;
import com.knulinkmoa.domain.directory.entity.Directory;
import com.knulinkmoa.domain.directory.exception.DirectoryErrorCode;
import com.knulinkmoa.domain.directory.repository.DirectoryRepository;
import com.knulinkmoa.domain.site.entity.Site;
import com.knulinkmoa.domain.site.exception.SiteErrorCode;
import com.knulinkmoa.domain.site.repository.SiteRepository;
import com.knulinkmoa.domain.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectoryService {

    private final DirectoryRepository directoryRepository;
    private final SiteRepository siteRepository;

    /**
     * 하위 디렉토리 추가
     * @param request
     * @return
     */
    @Transactional
    public Long saveDirectory(SaveRequest request, Long parentId) {

        Directory directory = Directory.builder()
                .directoryName(request.name())
                .parentId(parentId)
                .build();

        Directory saveDirectory = directoryRepository.save(directory);
        return saveDirectory.getId();
    }

    /**
     * 디렉토리에 시이트 추가
     * @param request
     * @return
     */
    @Transactional
    public Long saveSite(SaveRequest request, Long id) {

        Directory findDirectory = directoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        Site site = Site.builder()
                .siteName(request.name())
                .url(request.url())
                .directory(findDirectory)
                .build();

       siteRepository.save(site);
       return site.getId();
    }

    /**
     * 디렉토리에 있는 사이트 읽어오기
     * @param id
     * @return
     */
    public ReadResponse readDirectory(Long id) {

        Directory findDirectory = directoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        List<String> siteUrl = findDirectory.getSiteList().stream()
                .map(Site::getUrl)
                .toList();

        List<String> siteName = findDirectory.getSiteList().stream()
                .map(Site::getSiteName)
                .toList();

        ReadResponse readResponse = ReadResponse.builder()
                .siteUrl(siteUrl)
                .siteName(siteName)
                .build();

        return readResponse;
    }

    /**
     * 디렉토리 이름 업데이트
     * @param request
     * @param id
     * @return
     */
    @Transactional
    public Long updateDirectory(UpdateRequest request, Long id) {

        Directory findDirectory = directoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        findDirectory.update(request);

        directoryRepository.save(findDirectory);
        return findDirectory.getId();
    }

    /**
     * 사이트 이름과 url 업데이트
     * @param request
     * @return
     */

    /**
     * 디렉토리 삭제하기
     * @param id
     */
    @Transactional
    public void deleteDirectory(Long id) {

        Directory findDirectory = directoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        directoryRepository.delete(findDirectory);
    }

    /**
     * 사이트 삭제하기
     * @param id
     */
    @Transactional
    public void deleteSite(Long id) {

        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new GlobalException(SiteErrorCode.SITE_NOT_FOUND));

        siteRepository.delete(site);
    }
}
