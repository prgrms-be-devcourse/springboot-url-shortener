package org.daehwi.shorturl.exception;

import lombok.extern.slf4j.Slf4j;
import org.daehwi.shorturl.controller.dto.ApiResponse;
import org.daehwi.shorturl.controller.dto.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        log.error("Exception Occurred: ", e);
        return ResponseEntity.internalServerError().body(ApiResponse.of(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
        ResponseStatus errorResponseStatus = e.getErrorResponseStatus();
        return ResponseEntity.status(errorResponseStatus.getHttpStatus()).body(ApiResponse.of(errorResponseStatus));
    }
}
