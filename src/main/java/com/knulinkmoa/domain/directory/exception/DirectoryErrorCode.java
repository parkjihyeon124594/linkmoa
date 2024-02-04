package com.knulinkmoa.domain.directory.exception;

import com.knulinkmoa.domain.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum DirectoryErrorCode implements ErrorCode {

    DIRECTORY_NOT_FOUND(HttpStatus.NOT_FOUND,"NoneExistent directory");
    private final HttpStatus status;
    private final String message;

    DirectoryErrorCode(HttpStatus status, String message) {
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
