package com.progms.shorturl.global.handler;

import com.progms.shorturl.global.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleException(IllegalArgumentException illegalArgumentException) {
        return handleExceptionInternal(illegalArgumentException.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(handleExceptionInternal(ex.getMessage(), ex.getFieldErrors()));
    }

    private ErrorResponse handleExceptionInternal(String message) {
        return ErrorResponse.builder()
                .message(message)
                .build();
    }

    private ErrorResponse handleExceptionInternal(String message, List<FieldError> fieldErrors) {
        List<ErrorResponse.ErrorStatus> errors = fieldErrors.stream()
                .map(ErrorResponse.ErrorStatus::of)
                .toList();

        return ErrorResponse.builder()
                .message(message)
                .errors(errors)
                .build();
    }
}
