package com.knulinkmoa.domain.directory.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ReadResponse(List<String> siteName, List<String> siteUrl){
}
