package com.programmers.urlshortener.url.service;

import org.springframework.stereotype.Service;

import com.programmers.urlshortener.algorithm.Algorithm;
import com.programmers.urlshortener.url.dto.UrlShortenRequest;
import com.programmers.urlshortener.url.dto.UrlShortenResponse;
import com.programmers.urlshortener.url.entity.Url;
import com.programmers.urlshortener.url.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

	public static final String BASE_URL = "http://localhost:8080/";

	private final UrlRepository urlRepository;

	private final Algorithm<Long, String> algorithm;

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

	public String getOriginalUrl(String shorteningKey) {
		Long decodedId = algorithm.decode(shorteningKey);
		Url url = urlRepository.findById(decodedId)
			.orElseThrow(() -> new RuntimeException("잘못된 URL 요청입니다."));
		url.increaseTotalClicks();
		return url.getOriginalUrl();
	}

	public int countTotalClicks(String shorteningKey) {
		Long decodedId = algorithm.decode(shorteningKey);
		Url url = urlRepository.findById(decodedId)
			.orElseThrow(() -> new RuntimeException("잘못된 URL 요청입니다."));
		return url.getTotalClicks();
	}
}
