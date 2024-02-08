package com.knulinkmoa.domain.directory.service;

import com.knulinkmoa.domain.directory.dto.request.DirectorySaveRequest;
import com.knulinkmoa.domain.directory.entity.Directory;
import com.knulinkmoa.domain.directory.exception.DirectoryErrorCode;
import com.knulinkmoa.domain.directory.repository.DirectoryRelationRepository;
import com.knulinkmoa.domain.directory.repository.DirectoryRepository;
import com.knulinkmoa.domain.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DirectoryRelationService {

    private final DirectoryRelationRepository directoryRelationRepository;
    private final DirectoryRepository directoryRepository;

    @Transactional
    public void saveRelation(DirectorySaveRequest request, Long id) {

        Directory findDirectory = directoryRepository.findById(id)
                .orElseThrow(() -> new GlobalException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

        Long depth = 0L;
        if (findDirectory.getParentId() == 0) {
            DirectoryRelation directoryRelation = DirectoryRelation.builder()
                    .parentId(id)
                    .childId(id)
                    .depth(depth++)
                    .build();

            directoryRelationRepository.save(directoryRelation);
        }

        // id = 3

        while (findDirectory.getParentId() != 0) {

            DirectoryRelation directoryRelation = DirectoryRelation.builder()
                    .parentId(findDirectory.getParentId())
                    .childId(id)
                    .depth(depth++)
                    .build();

            directoryRelationRepository.save(directoryRelation);
        }

    }

}
