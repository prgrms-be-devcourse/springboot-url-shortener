package com.programmers.springbooturlshortener.domain.url;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByOriginUrl(String originUrl);

    Optional<Url> findByShortUrl(String shortUrl);
}