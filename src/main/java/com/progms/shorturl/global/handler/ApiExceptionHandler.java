package com.progms.shorturl.global.handler;

import com.progms.shorturl.global.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
    })
    public ErrorResponse handleException(RuntimeException runtimeException) {
        return handleExceptionInternal(runtimeException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handlerException(MethodArgumentNotValidException validException) {
        return handleExceptionInternal(validException.getMessage(), validException.getFieldErrors());
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
