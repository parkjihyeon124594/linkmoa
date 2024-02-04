package com.knulinkmoa.domain.directory.repository;

import com.knulinkmoa.domain.directory.entity.Directory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {
}
