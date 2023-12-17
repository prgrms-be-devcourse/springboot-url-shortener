package com.example.urlmanagement.presentation;

import com.example.urlmanagement.exception.UrlException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UrlException.class)
    public ResponseEntity<String> catchCustomUrlException(UrlException e) {
        log.error(e.getErrorCode().getErrorMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getErrorHttpStatus()).body(e.getErrorCode().getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> catchGlobalException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 예외가 발생했습니다.");
    }
}
