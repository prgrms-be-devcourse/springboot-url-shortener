package com.seungwon.springbooturlshortener.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seungwon.springbooturlshortener.application.dto.UrlCreateRequest;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateResponse;
import com.seungwon.springbooturlshortener.domain.Url;
import com.seungwon.springbooturlshortener.infrastructure.UrlJpaRepository;

import lombok.RequiredArgsConstructor;
//TODO: 약 1~100000개 -> 개수 많아지는 경우 처리?
//TODO : 각 url 별로 같은 주소면 매번 같은 short-url? 기존 서비스는 그렇지 않음, 그러면 각 사이트별로 shorten 횟수를 저장하는 별도의 DB?
//TODO : short url key가 같아지는 경우 처리
@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {

	private final UrlJpaRepository urlJpaRepository;

	private final UrlShortenService urlShortenService;

	public UrlCreateResponse saveUrl(UrlCreateRequest urlCreateRequest) {
		Url url = UrlCreateRequest.from(urlCreateRequest);
		urlJpaRepository.save(url);

		String shortUrlKey = urlShortenService.shorten(url);
		url.saveShortUrlKey(shortUrlKey);

		return new UrlCreateResponse(shortUrlKey);
	}

	public String loadUrl(String shortUrlKey) {
		Url url = urlJpaRepository.findAllByShortUrlKey(shortUrlKey);

		return url.getOriginalUrl();
	}
}
