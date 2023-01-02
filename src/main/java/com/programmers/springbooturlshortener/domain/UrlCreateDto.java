package com.programmers.springbooturlshortener.domain;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

public record UrlCreateDto(@NotBlank @URL @Column(nullable = false) String originUrl,
						   @NotBlank @Column(nullable = false) Algorithm algorithm) {

	public Url toEntity(String shortUrl) {
		return Url.builder()
			.originUrl(originUrl)
			.shortUrl(shortUrl)
			.algorithm(algorithm)
			.requestCount(1L)
			.build();
	}
}