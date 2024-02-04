package com.knulinkmoa.domain.site.dto.request;

import com.knulinkmoa.domain.directory.entity.Directory;

import java.util.Dictionary;

public record SiteSaveRequest(Long id,String url,String name, Directory directory) {
}
