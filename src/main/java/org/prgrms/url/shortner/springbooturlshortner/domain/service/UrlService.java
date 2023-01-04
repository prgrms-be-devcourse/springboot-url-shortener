package org.prgrms.url.shortner.springbooturlshortner.domain.service;

import java.util.Optional;

import org.prgrms.url.shortner.springbooturlshortner.domain.Url;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateRequest;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.CreateResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.dto.SearchResponse;
import org.prgrms.url.shortner.springbooturlshortner.domain.repository.UrlJpaRepository;
import org.prgrms.url.shortner.springbooturlshortner.domain.service.converter.UrlConverter;
import org.prgrms.url.shortner.springbooturlshortner.global.error.exception.EntityNotFoundException;
import org.prgrms.url.shortner.springbooturlshortner.global.error.exception.MaxAttemptException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

	private static final int MAX_ATTEMPT = 5;
	private final UrlJpaRepository urlJpaRepository;
	private final UrlConverter urlConverter;

	@Transactional
	public CreateResponse create(CreateRequest createRequest) {
		Url url = urlConverter.getUrl(createRequest);
		executeAlgorithm(url);
		urlJpaRepository.save(url);
		return urlConverter.getCreateResponse(url.getShortenUrl());
	}

	private void executeAlgorithm(Url url) {
		int cnt = 0;
		while (MAX_ATTEMPT > cnt++) {
			String shortenUrl = url.createShortenUrl();
			Optional<Url> findUrl = urlJpaRepository.findUrlByShortenUrlEquals(shortenUrl);
			if (findUrl.isEmpty()) {
				url.allocateShortenUrl(shortenUrl);
				return;
			}
		}

		throw new MaxAttemptException();
	}

	public SearchResponse findByShortenUrl(String shortenUrl) {
		Optional<Url> url = urlJpaRepository.findUrlByShortenUrlEquals(shortenUrl);
		String originUrl = url.orElseThrow(EntityNotFoundException::new).getOriginUrl();
		return urlConverter.getSearchResponse(originUrl);
	}

}
