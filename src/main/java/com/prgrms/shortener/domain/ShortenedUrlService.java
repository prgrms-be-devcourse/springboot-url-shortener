package com.prgrms.shortener.domain;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShortenedUrlService {

  private final ShortenedUrlRepository urlRepository;
  private final ShortenedUrlFactory urlFactory;

  public ShortenedUrlService(ShortenedUrlRepository urlRepository, ShortenedUrlFactory urlFactory) {
    this.urlRepository = urlRepository;
    this.urlFactory = urlFactory;
  }

  public String shorten(String originalUrl) {

    Optional<ShortenedUrl> savedUrl = urlRepository.findByOriginalUrl(originalUrl);
    if (savedUrl.isPresent()) {
      return savedUrl.get().getShortenedKey();
    }

    ShortenedUrl createdUrl = urlFactory.createShortenedUrl(originalUrl);

    return createdUrl.getShortenedKey();
  }

  public Optional<String> findOriginalUrlByKey(String key) {
    Optional<ShortenedUrl> storedUrl = urlRepository.findByShortenedKey(key);
    if (storedUrl.isEmpty()) {
      return Optional.empty();
    }
    urlRepository.increaseCount(storedUrl.get().getId());
    return Optional.of(storedUrl.get().getOriginalUrl());
  }
}
