package com.programmers.springbooturlshortener.domain.url.dto;

import com.programmers.springbooturlshortener.domain.url.Url;

public record UrlServiceRequestDto(String originUrl, String algorithm) {

	public Url toEntity() {
		return Url.builder()
			.originUrl(originUrl)
			.algorithm(algorithm)
			.build();
	}
}
