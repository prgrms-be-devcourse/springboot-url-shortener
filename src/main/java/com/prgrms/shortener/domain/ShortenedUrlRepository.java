package com.prgrms.shortener.domain;

import java.util.Optional;

public interface ShortenedUrlRepository {

  Optional<ShortenedUrl> findByOriginalUrl(String originalUrl);

  Optional<ShortenedUrl> findByShortenedKey(String key);
}
