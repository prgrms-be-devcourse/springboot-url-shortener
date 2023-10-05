package com.example.kdtspringbooturlshortener.global.error.handler;

import com.example.kdtspringbooturlshortener.global.error.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleNullPointerException(RuntimeException e) {
        return makeResponseErrorFormat(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public ErrorResponse handleNullPointerException(NullPointerException e) {
        return makeResponseErrorFormat(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return makeResponseErrorFormat(e.getMessage(), e);
    }

    private ErrorResponse makeResponseErrorFormat(String message) {
        return new ErrorResponse(message, null);
    }

    private ErrorResponse makeResponseErrorFormat(String message, BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult()
                .getFieldErrors();
        List<ErrorResponse.FieldErrorStatus> errors = fieldErrors.stream()
                .map(ErrorResponse.FieldErrorStatus::of)
                .toList();

        return new ErrorResponse(message, errors);
    }
}
