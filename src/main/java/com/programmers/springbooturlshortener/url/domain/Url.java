package com.programmers.springbooturlshortener.url.domain;

import java.util.regex.Pattern;

import com.programmers.springbooturlshortener.global.domain.exception.EntityNotFound;
import com.programmers.springbooturlshortener.global.domain.exception.InvalidUrlRequest;
import com.programmers.springbooturlshortener.global.domain.utils.PrefixModifier;
import com.programmers.springbooturlshortener.url.presentation.request.UrlCreate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Url {

	private static final Pattern URL_REGEX = Pattern.compile(
		"(https?:\\/\\/)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)"
	);

	private final Long urlId;
	private final String originUrl;
	private String shortUrlKey;
	private long requestCount;

	@Builder
	private Url(Long urlId, String originUrl, String shortUrlKey, long requestCount) {
		validateUrl(originUrl);
		this.urlId = urlId;
		this.originUrl = originUrl;
		this.shortUrlKey = shortUrlKey;
		this.requestCount = requestCount;
	}

	public static Url from(UrlCreate urlCreate) {
		return Url.builder()
			.originUrl(PrefixModifier.modifyUrlPrefix(urlCreate.getOriginUrl()))
			.requestCount(0L)
			.build();
	}

	public void updateShortUrlKey(String shortUrlKey) {
		if (urlId == null) {
			throw new EntityNotFound();
		}

		this.shortUrlKey = shortUrlKey;
	}

	public void increaseRequestCount() {
		this.requestCount++;
	}

	private static void validateUrl(String url) {
		if (!URL_REGEX.matcher(url).matches()) {
			throw new InvalidUrlRequest();
		}
	}
}
