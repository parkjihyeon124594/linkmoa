package com.knulinkmoa.domain.global.exception.handler;

import com.knulinkmoa.domain.global.exception.GlobalException;
import com.knulinkmoa.domain.global.util.ApiUtil;
import com.knulinkmoa.domain.global.util.ApiUtil.ApiErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiErrorResult> businessLogicException(final GlobalException globalException) {
        HttpStatus status = globalException.getStatus();
        String code = globalException.getCode();
        String message = globalException.getMessage();
        return ResponseEntity.status(status).body(ApiUtil.error(status, code, message));
    }
}