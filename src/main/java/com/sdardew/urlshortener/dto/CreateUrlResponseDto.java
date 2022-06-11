package com.sdardew.urlshortener.dto;

public class CreateUrlResponseDto {
  private String shortUrl;

  public CreateUrlResponseDto(String shortUrl) {
    this.shortUrl = shortUrl;
  }

  public String getShortUrl() {
    return shortUrl;
  }
}
