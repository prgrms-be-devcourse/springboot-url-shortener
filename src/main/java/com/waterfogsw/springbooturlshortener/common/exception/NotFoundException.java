package com.waterfogsw.springbooturlshortener.common.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }
}
