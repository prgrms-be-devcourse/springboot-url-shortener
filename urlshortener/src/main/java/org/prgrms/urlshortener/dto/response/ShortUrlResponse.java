package org.prgrms.urlshortener.dto.response;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;

import lombok.Builder;

@Builder
public record ShortUrlResponse(
	String originUrl,
	String shortUrl,
	Algorithm algorithm,
	int hitCount
) {

	public static ShortUrlResponse from(Url url) {
		return ShortUrlResponse.builder()
			.originUrl(url.getOriginUrl())
			.shortUrl(url.getShortUrl())
			.algorithm(url.getAlgorithm())
			.hitCount(url.getHitCount())
			.build();
	}
}
