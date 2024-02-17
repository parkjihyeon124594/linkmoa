package com.knulinkmoa.global.exception.handler;

import com.knulinkmoa.global.exception.GlobalException;
import com.knulinkmoa.global.util.ApiUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ApiUtil.ApiErrorResult> businessLogicException(final GlobalException globalException) {
        HttpStatus status = globalException.getStatus();
        String code = globalException.getCode();
        String message = globalException.getMessage();
        return ResponseEntity.status(status).body(ApiUtil.error(status, code, message));
    }
}