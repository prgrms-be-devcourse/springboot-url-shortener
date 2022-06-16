package com.prgrms.shortener.domain;

public interface ShortenedUrlFactory {

  ShortenedUrl createShortenedUrl(String originalUrl);
}
