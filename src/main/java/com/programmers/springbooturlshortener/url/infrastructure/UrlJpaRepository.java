package com.programmers.springbooturlshortener.url.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlJpaRepository extends JpaRepository<UrlEntity, Long> {

	Optional<UrlEntity> findUrlEntityByOriginUrl(String url);
}
