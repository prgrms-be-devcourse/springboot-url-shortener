package com.programmers.springbooturlshortener.web.dto;

import javax.validation.constraints.NotBlank;

import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.domain.url.util.UrlValid;

public record UrlCreateDto(@UrlValid String originUrl, @NotBlank String algorithm) {

	public UrlServiceRequestDto toUrlServiceRequestDto() {
		return new UrlServiceRequestDto(this.originUrl(), this.algorithm());
	}
}