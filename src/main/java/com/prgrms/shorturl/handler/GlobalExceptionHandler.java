package com.prgrms.shorturl.handler;

import com.prgrms.shorturl.exception.HashingException;
import com.prgrms.shorturl.exception.InappropriateFormException;
import com.prgrms.shorturl.exception.NoSuchOriginalUrlException;
import com.prgrms.shorturl.exception.RedirectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HashingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<String> hashingException(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.status(500)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoSuchOriginalUrlException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<String> noSuchOriginalUrlException(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.status(404)
                .body(e.getMessage());
    }

    @ExceptionHandler(RedirectionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<String> redirectionException(Exception e) {
        log.info(e.getMessage());
        return ResponseEntity.status(500)
                .body(e.getMessage());
    }
}
