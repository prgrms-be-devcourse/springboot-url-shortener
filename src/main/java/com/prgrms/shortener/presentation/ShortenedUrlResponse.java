package com.prgrms.shortener.presentation;

import com.prgrms.shortener.domain.dto.UrlMetadata;
import lombok.Getter;

@Getter
public class ShortenedUrlResponse {

  private final String url;
  private Long count;
  private String originalUrl;

  public ShortenedUrlResponse(String host, String key) {
    this.url = host + key;
  }

  private ShortenedUrlResponse(String originalUrl, String host, String key, Long count) {
    this.url = host + key;
    this.count = count;
    this.originalUrl = originalUrl;
  }

  ;

  public static ShortenedUrlResponse from(UrlMetadata metadata, String host) {
    return new ShortenedUrlResponse(metadata.getOriginalUrl(), host, metadata.getKey(), metadata.getCount());
  }
}
