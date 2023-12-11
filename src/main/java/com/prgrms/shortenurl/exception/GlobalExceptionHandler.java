package com.prgrms.shortenurl.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final int NOT_FOUND_EXCEPTION = 404;
    private final int INTERNAL_SERVER_ERROR = 500;
    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<String> notFoundException(IllegalArgumentException e) {
        return ResponseEntity.status(NOT_FOUND_EXCEPTION).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> internalServerError(RuntimeException e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
