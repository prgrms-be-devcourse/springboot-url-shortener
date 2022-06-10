package com.prgrms.shortener.presentation;

import lombok.Getter;

@Getter
public class ShortenedUrlPayload {

  private final String url;

  public ShortenedUrlPayload(String url) {
    this.url = url;
  }
}
