package com.knulinkmoa.auth.exception;

import com.knulinkmoa.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum OAuth2Errorcode implements ErrorCode {

    FAILED_TO_GET_ACCESS_TOKEN(HttpStatus.NOT_FOUND,"NonExistent AccessToken");
    private final HttpStatus status;
    private final String message;

    OAuth2Errorcode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String code() {
        return this.name();
    }

    @Override
    public String message() {
        return this.message;
    }
}
