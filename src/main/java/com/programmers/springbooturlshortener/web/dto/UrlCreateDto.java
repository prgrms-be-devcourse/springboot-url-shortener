package com.programmers.springbooturlshortener.web.dto;

import javax.validation.constraints.NotBlank;

import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.domain.url.util.UrlValid;

public record UrlCreateDto(@UrlValid String originUrl, @NotBlank String algorithm) {

	private static final String HTTPS_PROTOCOL = "https://";
	private static final String HTTP_PROTOCOL = "http://";

	public UrlServiceRequestDto toUrlServiceRequestDto() {
		String removedProtocolUrl = this.removeProtocolFromOriginUrl();
		return new UrlServiceRequestDto(removedProtocolUrl, this.algorithm());
	}

	private String removeProtocolFromOriginUrl() {

		if (this.originUrl.startsWith(HTTPS_PROTOCOL)) {
			return this.originUrl.replace(HTTPS_PROTOCOL, "");
		}

		if (this.originUrl.startsWith(HTTP_PROTOCOL)) {
			return this.originUrl.replace(HTTP_PROTOCOL, "");
		}

		return this.originUrl;
	}
}