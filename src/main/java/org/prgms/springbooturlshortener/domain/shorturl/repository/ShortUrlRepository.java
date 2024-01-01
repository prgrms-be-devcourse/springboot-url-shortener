package org.prgms.springbooturlshortener.domain.shorturl.repository;

import org.prgms.springbooturlshortener.domain.shorturl.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {
    Optional<ShortUrl> findByOriginalUrl(String originalUrl);
}
