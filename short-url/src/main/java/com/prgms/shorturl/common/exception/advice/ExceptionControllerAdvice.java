package com.prgms.shorturl.common.exception.advice;

import com.prgms.shorturl.common.dto.ApiResponse;
import com.prgms.shorturl.url.exception.NotFoundUrlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ApiResponse<String> notFoundUrlExceptionHandler (NotFoundUrlException e) {
        log.error("NotFoundUrlException", e);
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ApiResponse<String> exceptionHandler (Exception e) {
        log.error("Exception", e);
        return new ApiResponse<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

}
