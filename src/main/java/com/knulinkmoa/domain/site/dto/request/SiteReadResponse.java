package com.knulinkmoa.domain.site.dto.request;

import lombok.Builder;

@Builder
public record SiteReadResponse(String siteName, String siteUrl) {
}
