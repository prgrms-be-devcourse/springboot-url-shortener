package com.prgrms.shorturl.repository;

import com.prgrms.shorturl.domain.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {
    Optional<ShortUrl> findByOriginalUrl(String originalUrl);
    Optional<ShortUrl> findByBase62Url(String shortUrl);
}
