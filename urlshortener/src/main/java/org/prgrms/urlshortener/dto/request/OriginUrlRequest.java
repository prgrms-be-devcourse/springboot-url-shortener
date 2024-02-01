package org.prgrms.urlshortener.dto.request;

import org.prgrms.urlshortener.domain.Algorithm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OriginUrlRequest(

	@NotBlank(message = "short url이 비어있으면 안됩니다.")
	String shortUrl,

	@NotNull(message = "알고리즘이 null이면 안됩니다.")
	Algorithm algorithm
) {
}
