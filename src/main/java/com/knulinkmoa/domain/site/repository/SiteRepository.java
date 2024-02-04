package com.knulinkmoa.domain.site.repository;

import com.knulinkmoa.domain.site.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
}
