package org.prgrms.urlshortener.dto.request;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ShortUrlCreateRequest(
	@NotBlank(message = "인코딩할 url은 비어있으면 안됩니다.")
	String originUrl,


	@NotNull(message = "알고리즘이 null이면 안됩니다.")
	Algorithm algorithm
) {

	public Url toEntity() {
		return new Url(originUrl, algorithm);
	}
}
