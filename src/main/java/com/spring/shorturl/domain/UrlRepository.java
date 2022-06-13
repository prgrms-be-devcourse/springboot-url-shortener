package com.spring.shorturl.domain;

import com.spring.shorturl.domain.data.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByOriginUrl(String originUrl);

    Optional<Url> findByShortUrl(String shortUrl);
}
