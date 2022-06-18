package com.example.springbooturlshortener.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
  /* 400 BAD_REQUEST : 잘못된 요청 */
  INVALID_URL(BAD_REQUEST, "잘못된 URL 정보입니다."),
  INVALID_KEY(BAD_REQUEST, "잘못된 key 정보입니다.");
  private final HttpStatus httpStatus;
  private final String detail;

  ErrorCode(HttpStatus httpStatus, String detail) {
    this.httpStatus = httpStatus;
    this.detail = detail;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getDetail() {
    return detail;
  }
}
