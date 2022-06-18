package com.example.springbooturlshortener.exception;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {

  private LocalDateTime timestamp = LocalDateTime.now();
  private String error;
  private String message;

  private ErrorResponse(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
    return ResponseEntity.status(errorCode.getHttpStatus())
                         .body(
                           new ErrorResponse(
                             errorCode.getHttpStatus().name(),
                             errorCode.getDetail())
                         );
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }
}
