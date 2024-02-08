package com.knulinkmoa.domain.directory.service;

import com.knulinkmoa.domain.directory.dto.request.DirectorySaveRequest;
import com.knulinkmoa.domain.directory.dto.response.DirectoryReadResponse;
import com.knulinkmoa.domain.directory.entity.Directory;
import com.knulinkmoa.domain.directory.exception.DirectoryErrorCode;
import com.knulinkmoa.domain.directory.repository.DirectoryRepository;
import com.knulinkmoa.domain.global.exception.GlobalException;
import com.knulinkmoa.domain.member.entity.Member;
import com.knulinkmoa.domain.site.dto.request.SiteReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectoryService {

    private final DirectoryRepository directoryRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long saveDirectory(DirectorySaveRequest request, Long parentId) {

        Directory directory = Directory.builder()
                .directoryName(request.directoryName())
                .build();

        if (parentId != null) {
            Directory parentDirectory = directoryRepository.findById(parentId)
                    .orElseThrow(() -> new GlobalException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

            parentDirectory.addChildDirectory(directory);
        }


        directoryRepository.save(directory);

        return directory.getId();
    }

    /**
     * READ
     */
    public DirectoryReadResponse readDirectory(Long id) {
        Directory findDirectory = directoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        List<SiteReadResponse> result = DirectoryReadResponse.siteToSiteReadResponse(findDirectory.getSiteList());

        DirectoryReadResponse response = DirectoryReadResponse.builder()
                .directoryName(findDirectory.getDirectoryName())
                .siteList(result)
                .build();

        return response;
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateDirectory(DirectorySaveRequest request, Long id) {
        Directory directory = directoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        directory.update(request);
        directoryRepository.save(directory);

        return directory.getId();
    }

    /**
     * DELETE
     */
    @Transactional
    public void deleteDirectory(Long id) {
        Directory directory = directoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        directoryRepository.delete(directory);
    }
}
