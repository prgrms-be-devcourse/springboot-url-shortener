package com.dev.urlshortner.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.dev.urlshortner.config.UrlProperties;
import com.dev.urlshortner.domain.KeyEncodingGenerator;
import com.dev.urlshortner.dto.UrlResponse;
import com.dev.urlshortner.dto.UrlStatsResponse;
import com.dev.urlshortner.repository.UrlRepository;

@DataJpaTest
@Import({UrlService.class, UrlProperties.class})
class UrlServiceTest {

	@Autowired
	private UrlService urlService;
	@Autowired
	private UrlRepository urlRepository;
	@Autowired
	private UrlProperties urlProperties;

	@Test
	void 단축URL_생성_성공() {
		// given
		String originalUrl = "http://example.com";

		// when
		UrlResponse response = urlService.shortenUrl(originalUrl, KeyEncodingGenerator.BASE62);

		// then
		String shortKey = extractKey(response.shortUrl());
		assertTrue(urlRepository.existsByShortKey(shortKey));
	}

	@Test
	void 부적절한URL_단축URL_생성_실패() {
		// given
		String invalidUrl = "invalidurl";

		// when & then
		assertThrows(IllegalArgumentException.class, () -> urlService.shortenUrl(invalidUrl, KeyEncodingGenerator.BASE62));
	}

	@Test
	void 단축URL의_원본URL_조회() {
		// given
		String originalUrl = "http://example.com";
		UrlResponse response = urlService.shortenUrl(originalUrl, KeyEncodingGenerator.BASE62);
		String shortKey = extractKey(response.shortUrl());

		// when
		String foundOriginalUrl = urlService.getOriginalUrl(shortKey);

		// then
		assertEquals(originalUrl, foundOriginalUrl);
	}

	@Test
	void 단축URL_방문자수_조회() {
		// given
		String originalUrl = "http://example.com";
		UrlResponse response = urlService.shortenUrl(originalUrl, KeyEncodingGenerator.BASE62);
		String shortKey = extractKey(response.shortUrl());

		// when
		UrlStatsResponse stats = urlService.getUrlStats(shortKey);

		// then
		assertEquals(originalUrl, stats.originUrl());
	}

	private String extractKey(String shortUrl) {
		String baseUrl = urlProperties.getHost() + "/";
		return shortUrl.replace(baseUrl, "");
	}
}
