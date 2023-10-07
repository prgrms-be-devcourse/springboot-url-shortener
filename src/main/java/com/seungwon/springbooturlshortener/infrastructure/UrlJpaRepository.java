package com.seungwon.springbooturlshortener.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.seungwon.springbooturlshortener.domain.Url;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {

	Url findByShortUrlKey(@Param("shortUrlKey") String shortUrlKey);

	int countByOriginalUrl(@Param("originalUrl") String originalUrl);
}
