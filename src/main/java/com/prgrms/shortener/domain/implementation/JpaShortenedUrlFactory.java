package com.prgrms.shortener.domain.implementation;

import com.prgrms.shortener.domain.ShortenedUrl;
import com.prgrms.shortener.domain.ShortenedUrlFactory;
import com.prgrms.shortener.domain.encoding.EncodingStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaShortenedUrlFactory implements ShortenedUrlFactory {

  private final JpaShortenedUrlRepository urlRepository;
  private final EncodingStrategy shorteningStrategy;

  public JpaShortenedUrlFactory(JpaShortenedUrlRepository urlRepository, EncodingStrategy shorteningStrategy) {
    this.urlRepository = urlRepository;
    this.shorteningStrategy = shorteningStrategy;
  }

  @Override
  @Transactional
  public ShortenedUrl createShortenedUrl(String originalUrl) {

    ShortenedUrl urlEntity = new ShortenedUrl();
    urlEntity.assignOriginalUrl(originalUrl);

    urlEntity = urlRepository.save(urlEntity);
    String hashedKey = shorteningStrategy.encode(urlEntity.getId());
    urlEntity.assignKey(hashedKey);

    return urlRepository.save(urlEntity);

  }
}
