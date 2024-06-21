package org.prgrms.urlshortener.dto.response;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;

import lombok.Builder;

@Builder
public record EncodedUrlCreateResponse(
	String originUrl,
	String encodedUrl,
	Algorithm algorithm,
	int hitCount
) {

	public static EncodedUrlCreateResponse from(Url url) {
		return EncodedUrlCreateResponse.builder()
			.originUrl(url.getOriginUrl())
			.encodedUrl(url.getEncodedUrl())
			.algorithm(url.getAlgorithm())
			.hitCount(url.getHitCount())
			.build();
	}

}
