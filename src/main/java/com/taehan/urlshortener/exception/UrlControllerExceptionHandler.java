package com.taehan.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.net.URISyntaxException;

@ControllerAdvice
public class UrlControllerExceptionHandler {
    @ExceptionHandler({
            IllegalArgumentException.class,
            HttpMediaTypeNotSupportedException.class
    })
    public ResponseEntity<String> badRequestErrorHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
            URISyntaxException.class,
            Exception.class
    })
    public ResponseEntity<String> internalServerErrorHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
