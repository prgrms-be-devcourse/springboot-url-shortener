package com.example.springbooturlshortener.controller.dto;

import javax.validation.constraints.NotBlank;

public class UrlRequest {

  @NotBlank(message = "원본 URL은 필수 입력입니다.")
  private final String originalUrl;

  public UrlRequest(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }
}
