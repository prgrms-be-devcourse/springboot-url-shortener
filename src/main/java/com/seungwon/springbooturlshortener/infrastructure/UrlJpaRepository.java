package com.seungwon.springbooturlshortener.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seungwon.springbooturlshortener.domain.Url;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {

	Optional<Url> findByShortUrlKey(String shortUrlKey);

	int countByOriginalUrl(String originalUrl);
}
