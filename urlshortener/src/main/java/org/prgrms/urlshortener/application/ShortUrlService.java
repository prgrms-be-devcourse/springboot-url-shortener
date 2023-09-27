package org.prgrms.urlshortener.application;

import org.prgrms.urlshortener.domain.Url;
import org.prgrms.urlshortener.dto.request.ShortUrlCreateRequest;
import org.prgrms.urlshortener.dto.response.ShortUrlResponse;
import org.prgrms.urlshortener.respository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShortUrlService {

	private final AlgorithmInjector injector;

	private final UrlRepository shortUrlRepository;

	@Transactional
	public ShortUrlResponse encodeUrl(ShortUrlCreateRequest request) {
		Url url = request.toEntity();
		Url savedUrl = shortUrlRepository.save(url);

		String shortUrl = injector.encode(savedUrl.getOriginUrl(), savedUrl.getId(), request.algorithm());
		savedUrl.enrollShortUrl(shortUrl);

		return ShortUrlResponse.from(url);
	}

}
