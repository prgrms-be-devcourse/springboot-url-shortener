package com.prgrms.shortener.presentation;

import lombok.Getter;

@Getter
public class ShortenedUrlResponse {

  private final String url;

  public ShortenedUrlResponse(String host, String key) {
    this.url = host + key;
  }
}
