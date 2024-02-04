package com.knulinkmoa.domain.site.dto.response;

import com.knulinkmoa.domain.directory.entity.Directory;
import lombok.Builder;

@Builder
public record SiteReadResponse(Long id, String url, String name, Directory directory) {
}
