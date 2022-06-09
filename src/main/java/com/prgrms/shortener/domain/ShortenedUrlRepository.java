package com.prgrms.shortener.domain;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenedUrlRepository {

  Optional<ShortenedUrl> findByOriginalUrl(String originalUrl);
}
