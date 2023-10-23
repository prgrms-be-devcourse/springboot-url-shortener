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
public class UrlShortenerService {

	private final UrlJpaRepository urlJpaRepository;

	private final UrlService urlService;

	public UrlCreateResponse saveUrl(UrlCreateRequest urlCreateRequest) {
		Url url = UrlCreateRequest.from(urlCreateRequest);
		urlJpaRepository.save(url);

		String shortenType = urlCreateRequest.strategy();
		urlService.shorten(url, shortenType);
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
