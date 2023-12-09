package com.dev.shortenerurl.url.dto;

import com.dev.shortenerurl.url.domain.model.Url;
import com.dev.shortenerurl.url.dto.response.ShortenUrlInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlMapper {

	public static ShortenUrlInfo toEncodedUrlInfo(Url url) {
		return new ShortenUrlInfo(url.getEncodedUrl());
	}
}
