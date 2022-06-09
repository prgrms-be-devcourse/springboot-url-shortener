package com.prgrms.shortener.domain;

import org.springframework.stereotype.Component;

@Component
public interface ShortenedUrlFactory {

  ShortenedUrl createShortenedUrl(String originalUrl);
}
