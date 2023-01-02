package com.programmers.springbooturlshortener.domain.url.dto;

import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public record UrlCreateDto(@NotBlank @URL @Column(nullable = false) String originUrl,
						   @NotBlank @Column(nullable = false) String algorithm) {

	public UrlServiceRequestDto toUrlServiceRequestDto() {
		String removedProtocolUrl = this.removeProtocolFromOriginUrl();
		return new UrlServiceRequestDto(removedProtocolUrl, this.algorithm());
	}

	private String removeProtocolFromOriginUrl() {
		if (this.originUrl.startsWith("https://")) {
			return this.originUrl.replace("https://", "");
		}
		if (this.originUrl.startsWith("http://")) {
			return this.originUrl.replace("http://", "");
		}

		return this.originUrl;
	}
}