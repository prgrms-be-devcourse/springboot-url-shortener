package com.prgms.shorturl.common.exception.advice;

import com.prgms.shorturl.common.dto.ApiResponse;
import com.prgms.shorturl.url.exception.NotFoundUrlException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiResponse<String> validationHandler (MethodArgumentNotValidException e) {
        log.info("검증 오류 발생 errors={}", e);
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

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
