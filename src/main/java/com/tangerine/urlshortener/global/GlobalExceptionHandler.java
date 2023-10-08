package com.tangerine.urlshortener.global;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logger.warn("{} - {}", getClass().getSimpleName(), e);
        return "error/bad-request";
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public String runtimeExceptionHandler(RuntimeException e) {
        logger.warn("{} - {}", getClass().getSimpleName(), e);
        return "error/not-found";
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ExistMappingException.class)
    public String existMappingExceptionHandler(ExistMappingException e) {
        logger.warn("{} - {}", getClass().getSimpleName(), e);
        return "error/bad-request";
    }

}
