package com.dev.shortenerurl.url.domain.model.query;

public record OriginUrlModel(
	String originUrl,
	long requestCount
) {
}
