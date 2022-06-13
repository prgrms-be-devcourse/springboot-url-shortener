package com.prgrms.urlshortener.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR_LOG = "[ERROR] %s : %s";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        String errorMessage = extractErrorMessage(e);
        log.info(String.format(ERROR_LOG, e.getClass().getSimpleName(), errorMessage), e);
        return ResponseEntity
            .badRequest()
            .body(new ErrorResponse(errorMessage));
    }

    private String extractErrorMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
            .getAllErrors()
            .get(0)
            .getDefaultMessage();
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
        log.info(String.format(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage()), e);
        return ResponseEntity
            .status(e.getHttpStatus())
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(String.format(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage()), e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("서버에서 에러가 발생했습니다."));
    }

}
