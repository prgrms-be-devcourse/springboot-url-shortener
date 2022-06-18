package com.example.springbooturlshortener.exception;

import static com.example.springbooturlshortener.exception.ErrorCode.INVALID_URL;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(value = {MissingPathVariableException.class})
  public ResponseEntity<ErrorResponse> handleMissingPathVariableException() {
    return ErrorResponse.toResponseEntity(INVALID_URL);
  }

  @ExceptionHandler(value = {CustomException.class})
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    return ErrorResponse.toResponseEntity(e.getErrorCode());
  }
}
