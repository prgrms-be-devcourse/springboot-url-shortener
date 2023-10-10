package com.programmers.urlshortener.domain.url.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

	Optional<Url> findByShortUrl(String id);

	boolean existsByShortUrl(String shortUrl);
}
