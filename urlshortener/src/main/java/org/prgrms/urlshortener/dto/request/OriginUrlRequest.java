package org.prgrms.urlshortener.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OriginUrlRequest(

	@NotBlank(message = "short url이 비어있으면 안됩니다.")
	String shortUrl
) {
}
