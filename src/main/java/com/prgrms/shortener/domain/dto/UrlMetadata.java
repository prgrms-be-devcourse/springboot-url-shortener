package com.prgrms.shortener.domain.dto;

import com.prgrms.shortener.domain.ShortenedUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class UrlMetadata {

  private final String key;
  private final String originalUrl;
  private final Long count;

  public static UrlMetadata from(ShortenedUrl shortenedUrl) {
    return new UrlMetadata(shortenedUrl.getShortenedKey(), shortenedUrl.getOriginalUrl(), shortenedUrl.getCount());
  }
}
