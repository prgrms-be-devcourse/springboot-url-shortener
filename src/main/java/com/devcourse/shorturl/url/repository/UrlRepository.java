package com.devcourse.shorturl.url.repository;

import com.devcourse.shorturl.url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByLongUrl(String longUrl);
    Optional<Url> findByShortUrl(String shortUrl);

    Boolean existsByLongUrl(String longUrl);
}
