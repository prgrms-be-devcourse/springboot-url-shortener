package com.programmers.urlshortener.common.exception;

import static com.programmers.urlshortener.common.exception.ExceptionRule.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult()
            .getAllErrors()
            .stream()
            .map(FieldError.class::cast)
            .toList();

        log.error("[에러]: {}", e.getMessage(), e);

        errors.forEach(
            error -> log.error("메시지: {} / 원인: {} : {}", error.getDefaultMessage(), error.getField(),
                error.getRejectedValue()));

        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(BAD_REQUEST.getStatus().value())
            .message(BAD_REQUEST.getMessage())
            .build();

        return ResponseEntity.status(BAD_REQUEST.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("[에러]: {}", e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(e.getStatus().value())
            .message(e.getMessage())
            .build();

        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("[에러]: {}", e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
            .statusCode(INTERNAL_SERVER_ERROR.getStatus().value())
            .message(INTERNAL_SERVER_ERROR.getMessage())
            .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR.getStatus()).body(errorResponse);
    }
}
