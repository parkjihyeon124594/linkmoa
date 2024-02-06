package com.knulinkmoa.domain.site.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SiteReadResponse(String siteName, String siteUrl) {
}
