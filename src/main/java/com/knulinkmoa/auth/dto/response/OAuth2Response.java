package com.knulinkmoa.auth.dto.response;

public interface OAuth2Response {

    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();
}
