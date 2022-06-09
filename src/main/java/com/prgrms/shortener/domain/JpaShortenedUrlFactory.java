package com.prgrms.shortener.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaShortenedUrlFactory implements ShortenedUrlFactory {

  private final JpaShortenedUrlRepository urlRepository;
  private final UrlShorteningStrategy shorteningStrategy = number -> "aaabbbc" + number;

  public JpaShortenedUrlFactory(JpaShortenedUrlRepository urlRepository) {
    this.urlRepository = urlRepository;
  }

  @Override
  @Transactional
  public ShortenedUrl createShortenedUrl(String originalUrl) {

    ShortenedUrl urlEntity = new ShortenedUrl();
    urlEntity.assignOriginalUrl(originalUrl);

    urlEntity = urlRepository.saveAndFlush(urlEntity);
    String hashedKey = shorteningStrategy.shorten(urlEntity.getId());
    urlEntity.assignKey(hashedKey);

    return urlRepository.saveAndFlush(urlEntity);

  }
}
