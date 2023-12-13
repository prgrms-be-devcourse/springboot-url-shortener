package com.prgrms.shorturl.repository;

import com.prgrms.shorturl.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByOriginalUrl(String originalUrl);
    Optional<Url> findByShortUrl(String shortUrl);
    Optional<Url> findByHash(String hash);
}
