package org.prgrms.urlshortener.dto.response;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;

import lombok.Builder;

@Builder
public record UrlResponse(
	String originUrl,
	String shortUrl,
	Algorithm algorithm,
	int hitCount
) {

	public static UrlResponse from(Url url) {
		return UrlResponse.builder()
			.originUrl(url.getOriginUrl())
			.shortUrl(url.getShortUrl())
			.algorithm(url.getAlgorithm())
			.hitCount(url.getHitCount())
			.build();
	}

}
