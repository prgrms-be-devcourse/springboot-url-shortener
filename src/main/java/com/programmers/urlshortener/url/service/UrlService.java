package com.programmers.urlshortener.url.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.urlshortener.algorithm.Algorithm;
import com.programmers.urlshortener.url.dto.UrlShortenRequest;
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
	public UrlShortenResponse shortenUrl(UrlShortenRequest request) {
		Url url = new Url(request.originalUrl());
		Url savedUrl = urlRepository.save(url);
		String encodedKey = algorithm.encode(savedUrl.getId());
		url.addShorteningKey(encodedKey);

		return UrlShortenResponse.of(savedUrl.getOriginalUrl(), getShortenedUrl(encodedKey), encodedKey);
	}

	private String getShortenedUrl(String encodedKey) {
		return BASE_URL + encodedKey;
	}

	@Transactional
	public String getOriginalUrl(String shorteningKey) {
		Url url = getUrl(shorteningKey);
		url.increaseTotalClicks();

		return url.getOriginalUrl();
	}

	public UrlTotalClicksResponse countTotalClicks(String shorteningKey) {
		Url url = getUrl(shorteningKey);
		int totalClicks = url.getTotalClicks();
		String shortenedUrl = getShortenedUrl(url.getShorteningKey());

		return new UrlTotalClicksResponse(shortenedUrl, totalClicks);
	}

	private Url getUrl(String shorteningKey) {
		Long decodedId = algorithm.decode(shorteningKey);

		return urlRepository.findById(decodedId)
			.orElseThrow(() -> new RuntimeException("잘못된 URL 요청입니다."));
	}
}
