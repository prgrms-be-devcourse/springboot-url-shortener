package com.programmers.springbooturlshortener.url.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.springbooturlshortener.global.domain.converter.AlgorithmConverter;
import com.programmers.springbooturlshortener.global.domain.exception.EntityNotFound;
import com.programmers.springbooturlshortener.global.domain.utils.PrefixModifier;
import com.programmers.springbooturlshortener.url.application.port.UrlRepository;
import com.programmers.springbooturlshortener.url.domain.Url;
import com.programmers.springbooturlshortener.url.presentation.request.UrlCreate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

	private final UrlRepository urlRepository;
	private final AlgorithmConverter algorithmConverter;

	@Transactional
	public Url create(UrlCreate urlCreate) {
		String originUrl = PrefixModifier.modifyUrlPrefix(urlCreate.getOriginUrl());
		Optional<Url> url = urlRepository.findByOriginUrl(originUrl);

		if (url.isPresent()) {
			Url existsUrl = url.get();
			existsUrl.increaseRequestCount();

			return urlRepository.save(existsUrl);
		}

		Url newUrl = urlRepository.save(Url.from(urlCreate));
		String shortUrlKey = algorithmConverter.encode(newUrl.getUrlId());
		newUrl.updateShortUrlKey(shortUrlKey);

		return urlRepository.save(newUrl);
	}

	@Transactional
	public String getOriginUrl(String shortUrl) {
		String shortUrlKey = PrefixModifier.removeBeforeLastSlash(shortUrl);

		Url url = urlRepository.findById(algorithmConverter.decode(shortUrlKey))
			.orElseThrow(EntityNotFound::new);
		url.increaseRequestCount();

		return PrefixModifier.HTTPS_PREFIX + urlRepository.save(url).getOriginUrl();
	}
}
