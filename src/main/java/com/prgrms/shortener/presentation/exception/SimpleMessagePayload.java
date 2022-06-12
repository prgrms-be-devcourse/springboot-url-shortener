package com.prgrms.shortener.presentation.exception;

import lombok.Getter;

@Getter
public class SimpleMessagePayload {

  private final String message;

  public SimpleMessagePayload(String message) {
    this.message = message;
  }
}
