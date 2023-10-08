package com.seungwon.springbooturlshortener.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seungwon.springbooturlshortener.application.dto.UrlCreateRequest;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateResponse;
import com.seungwon.springbooturlshortener.domain.Url;
import com.seungwon.springbooturlshortener.exception.NotFoundException;
import com.seungwon.springbooturlshortener.infrastructure.UrlJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {

	private final UrlJpaRepository urlJpaRepository;

	public UrlCreateResponse saveUrl(UrlCreateRequest urlCreateRequest) {
		String shortenType = urlCreateRequest.strategy();
		UrlShortenService urlShortenService = ShortenServiceGenerator.from(shortenType);

		Url url = UrlCreateRequest.from(urlCreateRequest);
		urlJpaRepository.save(url);

		String shortUrlKey;
		shortUrlKey = urlShortenService.shorten(url);

		url.saveShortUrlKey(shortUrlKey);
		urlJpaRepository.save(url);

		return UrlCreateResponse.from(url);
	}

	@Transactional(readOnly = true)
	public String loadUrl(String shortUrlKey) {
		Url url = urlJpaRepository.findByShortUrlKey(shortUrlKey)
			.orElseThrow(NotFoundException::new);

		return url.getOriginalUrl();
	}

	@Transactional(readOnly = true)
	public int countUrl(String originalUrl) {
		int count = urlJpaRepository.countByOriginalUrl(originalUrl);

		return count;
	}
}
