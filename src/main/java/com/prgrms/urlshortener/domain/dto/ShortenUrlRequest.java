package com.prgrms.urlshortener.domain.dto;

import static com.prgrms.urlshortener.utils.Regexp.*;

import com.prgrms.urlshortener.domain.entity.UrlInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ShortenUrlRequest(
	@NotBlank @Pattern(regexp = URL) String url
) {
	public UrlInfo toEntity() {
		return new UrlInfo(url);
	}
}
