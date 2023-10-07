package com.seungwon.springbooturlshortener.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seungwon.springbooturlshortener.application.dto.UrlCountRequest;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateRequest;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateResponse;
import com.seungwon.springbooturlshortener.domain.Url;
import com.seungwon.springbooturlshortener.infrastructure.UrlJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {

	private final UrlJpaRepository urlJpaRepository;

	public UrlCreateResponse saveUrl(UrlCreateRequest urlCreateRequest) {
		UrlShortenService urlShortenService = UrlShortenService.from(urlCreateRequest.strategy());

		Url url = UrlCreateRequest.from(urlCreateRequest);
		urlJpaRepository.save(url);

		String shortUrlKey;
		shortUrlKey = urlShortenService.shorten(url);

		url.saveShortUrlKey(shortUrlKey);
		urlJpaRepository.save(url);

		return UrlCreateResponse.from(url);
	}

	public String loadUrl(String shortUrlKey) {
		Url url = urlJpaRepository.findByShortUrlKey(shortUrlKey);

		return url.getOriginalUrl();
	}

	public int countUrl(String originalUrl) {
		int count = urlJpaRepository.countByOriginalUrl(originalUrl);

		return count;
	}
}
