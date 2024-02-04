package com.knulinkmoa.domain.site.exception;

import ch.qos.logback.core.spi.ErrorCodes;
import com.knulinkmoa.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum SiteErrorCode implements ErrorCode {

    SITE_NOT_FOUND(HttpStatus.NOT_FOUND,"Noneexistent site");
    private final HttpStatus status;
    private final String message;

    SiteErrorCode(HttpStatus status, String message) {
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
