package com.tangerine.urlshortener.global;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.aspectj.weaver.ast.Not;
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
        logger.error("Bad-Request : {0}", e);
        return "error/bad-request";
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundMappingException.class)
    public String notFoundMappingException(NotFoundMappingException e) {
        logger.warn("Not-Found : {0}", e);
        return "error/not-found";
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ExistMappingException.class)
    public String existMappingExceptionHandler(ExistMappingException e) {
        logger.warn("{} - {}", getClass().getSimpleName(), e);
        return "error/bad-request";
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public String runtimeExceptionHandler(RuntimeException e) {
        logger.error("Runtime Error : {0}", e);
        return "error/bad-request";
    }

}
