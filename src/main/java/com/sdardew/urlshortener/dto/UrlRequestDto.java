package com.sdardew.urlshortener.dto;

public class UrlRequestDto {
  private String shorUrl;

  public UrlRequestDto(String shorUrl) {
    this.shorUrl = shorUrl;
  }

  public String getShorUrl() {
    return shorUrl;
  }
}
