package com.programmers.urlshortener.domain.url.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.programmers.urlshortener.domain.encoder.domain.EncoderType;
import com.programmers.urlshortener.domain.url.domain.Url;

public record ShortUrlCreateRequest(
	@Size(min = 5, max = 500)
	@NotBlank
	String originalUrl,

	@NotBlank
	String encoderType
) {

	public static Url toUrl(ShortUrlCreateRequest shortUrlCreateRequest) {

		return Url.builder()
			.originalUrl(shortUrlCreateRequest.originalUrl)
			.encoderType(EncoderType.findEncoderType(shortUrlCreateRequest.encoderType))
			.build();
	}
}
