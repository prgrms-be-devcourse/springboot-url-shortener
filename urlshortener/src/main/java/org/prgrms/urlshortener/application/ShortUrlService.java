package org.prgrms.urlshortener.application;

import org.prgrms.urlshortener.domain.Url;
import org.prgrms.urlshortener.dto.request.OriginUrlRequest;
import org.prgrms.urlshortener.dto.request.ShortUrlCreateRequest;
import org.prgrms.urlshortener.dto.response.OriginUrlResponse;
import org.prgrms.urlshortener.dto.response.UrlResponse;
import org.prgrms.urlshortener.respository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShortUrlService {

	private static final String BASE_URL = "/bent.ly/";

	private final AlgorithmInjector injector;

	private final UrlRepository urlRepository;

	@Transactional
	public UrlResponse encodeUrl(ShortUrlCreateRequest request) {
		Url url = request.toEntity();
		Url savedUrl = urlRepository.save(url);

		String shortUrl = injector.encode(savedUrl.getOriginUrl(), savedUrl.getId(), request.algorithm());
		shortUrl = BASE_URL + shortUrl;
		savedUrl.enrollShortUrl(shortUrl);

		UrlResponse response = UrlResponse.from(savedUrl);

		return UrlResponse.from(savedUrl);
	}

	@Transactional(readOnly = true)
	public OriginUrlResponse getOriginUrl(OriginUrlRequest request) {
		Url url = urlRepository.findByShortUrl(request.shortUrl())
			.orElseThrow(() -> new RuntimeException("존재하지 않는 shortUrl입니다."));

		OriginUrlResponse response = OriginUrlResponse.from(url);

		return response;
	}

}
