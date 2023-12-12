package com.pgms.shorturlcoredomain.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getCode(), e.getMessage());
        log.warn(">>>>> Custom Exception 발생, {}", e);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        ExceptionResponse exceptionResponse = new ExceptionResponse("500/00001", e.getMessage());
        log.error(">>>>> Unexpected Exception 발생, {}", e);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
