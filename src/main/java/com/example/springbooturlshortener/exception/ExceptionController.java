package com.example.springbooturlshortener.exception;

import static com.example.springbooturlshortener.exception.ErrorCode.INVALID_KEY;
import javax.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(value = {ConstraintViolationException.class, MissingPathVariableException.class,
    HttpMediaTypeException.class})
  public ResponseEntity<ErrorResponse> handleConstraintViolationException() {
    return ErrorResponse.toResponseEntity(INVALID_KEY);
  }

  @ExceptionHandler(value = {CustomException.class})
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    return ErrorResponse.toResponseEntity(e.getErrorCode());
  }
}
