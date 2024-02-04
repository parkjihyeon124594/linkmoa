package com.knulinkmoa.domain.site.dto.request;

import com.knulinkmoa.domain.directory.entity.Directory;

public record SiteUpdateRequset(String url, String name, Directory directory) {
}
