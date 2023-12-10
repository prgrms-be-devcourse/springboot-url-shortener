package com.dev.shortenerurl.url.dto;

import com.dev.shortenerurl.url.domain.model.Url;
import com.dev.shortenerurl.url.domain.model.query.OriginUrlModel;
import com.dev.shortenerurl.url.dto.response.OriginUrlInfo;
import com.dev.shortenerurl.url.dto.response.ShortenUrlInfo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlMapper {

	private static final String URL_PREFIX = "http://localhost:8080/url/";

	public static ShortenUrlInfo toEncodedUrlInfo(Url url) {
		return new ShortenUrlInfo(URL_PREFIX + url.getEncodedUrl());
	}

	public static OriginUrlInfo toOriginUrlInfo(OriginUrlModel originUrlModel) {
		return new OriginUrlInfo(originUrlModel.originUrl());
	}
}
