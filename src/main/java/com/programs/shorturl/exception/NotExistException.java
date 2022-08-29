package com.programs.shorturl.exception;

public class NotExistException extends RuntimeException{
  private static final String MESSAGE = "url이 없습니다";

  public NotExistException() {
    super(MESSAGE);
  }
  public NotExistException(String message) {
    super(message);
  }

}
