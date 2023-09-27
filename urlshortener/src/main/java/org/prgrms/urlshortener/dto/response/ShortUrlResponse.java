package org.prgrms.urlshortener.dto.response;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;

public record ShortUrlResponse(
	String shortUrl,
	Algorithm algorithm,
	int hitCount
) {

	public static ShortUrlResponse from(Url url) {
		return new ShortUrlResponse(url.getShortUrl(), url.getAlgorithm(), url.getHitCount());
	}
}
