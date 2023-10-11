package com.prgrmrs.urlshortener.global;

import static com.prgrmrs.urlshortener.exception.ErrorCode.INTERNAL_SERVER_ERROR;

import com.prgrmrs.urlshortener.exception.ErrorCode;
import com.prgrmrs.urlshortener.exception.UrlShortenerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UrlShortenerException.class)
    public ResponseEntity<ErrorResponse> placeExceptionHandler(UrlShortenerException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        logger.error("UrlShortenerException: {}", errorResponse);

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalServerErrorExceptionHandler(Exception exception) {
        ErrorCode errorCode = INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        logger.error("Exception: {}", errorResponse);

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }
}