package com.prgrms.shortener.exception;

public enum ErrorMessage {

  INVALID_INPUT_URL("URL은 NULL일 수 없습니다."),
  INVALID_INPUT_KEY("KEY는 NULL일 수 없습니다."),
  URL_NOT_FOUND("해당 url을 찾을 수 없습니다."),
  REDIRECT_ERROR("리다이렉트에 실패했습니다.");

  private final String message;

  ErrorMessage(String message) {

    this.message = message;
  }

  public String getMessage() {

    return message;
  }
}
