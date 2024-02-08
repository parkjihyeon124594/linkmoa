package com.knulinkmoa.domain.directory.dto.response;

import com.knulinkmoa.domain.site.entity.Site;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record DirectoryReadResponse(String directoryName, List<SiteReadResponse> siteList) {

    public static List<SiteReadResponse> siteToSiteReadResponse(List<Site> sites) {

        List<SiteReadResponse> result = new ArrayList<>();

        for (Site site : sites) {
            SiteReadResponse response = SiteReadResponse.builder()
                    .siteName(site.getSiteName())
                    .siteUrl(site.getUrl())
                    .build();

            result.add(response);
        }

        return result;
    }
}
