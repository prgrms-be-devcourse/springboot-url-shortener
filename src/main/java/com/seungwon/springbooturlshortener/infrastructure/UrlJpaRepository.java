package com.seungwon.springbooturlshortener.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seungwon.springbooturlshortener.domain.Url;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {
	Url findAllByShortUrlKey(String shortUrlKey);
}
