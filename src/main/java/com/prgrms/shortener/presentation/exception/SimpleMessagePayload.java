package com.prgrms.shortener.presentation.exception;

import lombok.Getter;

@Getter
class SimpleMessagePayload {

  private final String message;

  SimpleMessagePayload(String message) {
    this.message = message;
  }
}
