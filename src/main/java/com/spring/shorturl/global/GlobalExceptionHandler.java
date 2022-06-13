package com.spring.shorturl.global;

import com.spring.shorturl.domain.controller.ApiResponse;
import com.spring.shorturl.global.exception.NotFoundEntityByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e.getMessage(), e);
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), e);
    }


    @ExceptionHandler(NotFoundEntityByIdException.class)
    protected ApiResponse<?> handleEntityNotFoundEntityByIdException(NotFoundEntityByIdException e) {
        log.warn("NotFoundEntityByIdException {}", e.getMessage(), e);
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e);
    }
}
