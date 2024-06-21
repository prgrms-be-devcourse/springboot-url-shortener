package org.prgrms.urlshortener.dto.response;

import org.prgrms.urlshortener.domain.Url;

public record OriginUrlResponse(
	String originUrl
) {

	public static OriginUrlResponse from(Url url) {
		return new OriginUrlResponse(url.getOriginUrl());
	}

}
