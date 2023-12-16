package com.dev.urlshortner.service;

import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.urlshortner.dto.UrlResponse;
import com.dev.urlshortner.dto.UrlStatsResponse;
import com.dev.urlshortner.domain.Url;
import com.dev.urlshortner.domain.KeyEncodingType;
import com.dev.urlshortner.config.UrlProperties;
import com.dev.urlshortner.repository.UrlRepository;
import com.dev.urlshortner.util.ShortKeyGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {
	private static final int MAX_ATTEMPTS = 100;
	private static final String PATH_DELIMITER = "/";
	private static final Pattern URL_PATTERN = Pattern.compile(
		"^(https?://)([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([/\\w \\.-]*)*/?$");

	private final UrlRepository urlRepository;
	private final UrlProperties urlProperties;

	@Transactional
	public UrlResponse shortenUrl(String originalUrl, KeyEncodingType keyEncodingType) {
		validateUrl(originalUrl);
		if (isShortenedUrl(originalUrl)) {
			throw new IllegalArgumentException("URL is already shortened");
		}

		ShortKeyGenerator shortKeyGenerator = keyEncodingType.getEncoder();
		String shortKey = generateUniqueKey(shortKeyGenerator);

		Url url = new Url(shortKey, originalUrl);
		urlRepository.save(url);

		String shortUrl = urlProperties.getHost() + PATH_DELIMITER + shortKey;
		return UrlResponse.of(shortUrl, originalUrl);
	}

	private void validateUrl(String url) {
		if (url == null || url.trim().isEmpty()) {
			throw new IllegalArgumentException("URL cannot be null or empty");
		}

		Matcher matcher = URL_PATTERN.matcher(url);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid URL: Malformed URL provided");
		}
	}

	private String generateUniqueKey(ShortKeyGenerator shortKeyGenerator) {
		String key;
		int attempts = 0;
		do {
			key = shortKeyGenerator.generateKey(urlProperties.getKeyLength());
			if (++attempts > MAX_ATTEMPTS) {
				throw new IllegalStateException("Failed to generate a unique short key after " + MAX_ATTEMPTS + " attempts.");
			}
		} while (urlRepository.existsByShortKey(key));
		return key;
	}

	private boolean isShortenedUrl(String url) {
		String shortenedUrlPrefix = urlProperties.getHost();
		return url.startsWith(shortenedUrlPrefix);
	}

	@Transactional
	public String getOriginalUrl(String shortKey) {
		Url url = urlRepository.findByShortKey(shortKey)
			.orElseThrow(() -> new NoSuchElementException("No URL found for shortKey: " + shortKey));
		url.incrementVisitCount();
		urlRepository.save(url);
		return url.getOriginalUrl();
	}

	@Transactional(readOnly = true)
	public UrlStatsResponse getUrlStats(String shortKey) {
		return urlRepository.findByShortKey(shortKey)
			.map(UrlStatsResponse::of)
			.orElseThrow(() -> new NoSuchElementException("No URL found for shortKey: " + shortKey));
	}

}
