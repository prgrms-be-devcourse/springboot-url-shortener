package com.programmers.urlshortener.url.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.urlshortener.algorithm.Algorithm;
import com.programmers.urlshortener.error.exception.BaseException;
import com.programmers.urlshortener.error.exception.ErrorCode;
import com.programmers.urlshortener.url.dto.UrlShortenResponse;
import com.programmers.urlshortener.url.dto.UrlTotalClicksResponse;
import com.programmers.urlshortener.url.entity.Url;
import com.programmers.urlshortener.url.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

	public static final String BASE_URL = "http://localhost:8080/";

	private final UrlRepository urlRepository;

	private final Algorithm<Long, String> algorithm;

	@Transactional
	public UrlShortenResponse shortenUrl(final String originalUrl) {
		if (originalUrl.isBlank()) {
			throw new BaseException(ErrorCode.URL_NOT_VALID);
		}

		final Url url = new Url(originalUrl);
		final Url savedUrl = urlRepository.save(url);
		final String shorteningKey = algorithm.encode(savedUrl.getId());
		url.addShorteningKey(shorteningKey);

		return UrlShortenResponse.of(originalUrl, getShortenedUrl(shorteningKey), shorteningKey);
	}

	@Transactional
	public String getOriginalUrl(final String shorteningKey) {
		final Url url = getUrl(shorteningKey);
		url.increaseTotalClicks();

		return url.getOriginalUrl();
	}

	public UrlTotalClicksResponse getTotalClicks(final String shorteningKey) {
		final Url url = getUrl(shorteningKey);
		final int totalClicks = url.getTotalClicks();
		final String shortenedUrl = getShortenedUrl(shorteningKey);

		return new UrlTotalClicksResponse(shortenedUrl, totalClicks);
	}

	private Url getUrl(final String shorteningKey) {
		final Long urlId = algorithm.decode(shorteningKey);

		return urlRepository.findById(urlId)
			.orElseThrow(() -> new BaseException(ErrorCode.URL_NOT_FOUND));
	}

	private String getShortenedUrl(final String shorteningKey) {
		return BASE_URL + shorteningKey;
	}
}
