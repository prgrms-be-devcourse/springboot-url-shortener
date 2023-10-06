package com.prgrms.urlshortener.domain.dto;

import static com.prgrms.urlshortener.utils.Regexp.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ShortenUrlRequest(
	@NotBlank @Pattern(regexp = URL) String url
) {
}
