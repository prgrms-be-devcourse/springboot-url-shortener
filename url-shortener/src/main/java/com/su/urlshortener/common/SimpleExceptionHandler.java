package com.su.urlshortener.common;

import com.su.urlshortener.url.exception.UrlNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
public class SimpleExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UrlNotFoundException.class)
    protected String urlNotFoundExceptionHandler(UrlNotFoundException e) {
        log.warn("SimpleExceptionHandler - urlNotFoundException", e);
        return "error/404";
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    protected String illegalArgumentExceptionExceptionHandler(IllegalArgumentException e) {
        log.warn("SimpleExceptionHandler - illegalArgumentExceptionException", e);
        return "error/5xx";
    }
}
