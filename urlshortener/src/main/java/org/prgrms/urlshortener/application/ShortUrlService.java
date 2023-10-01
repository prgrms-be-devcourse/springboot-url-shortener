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

	private static final String BASE_URL = "bent.ly";

	private final Encoder encoder;

	private final UrlRepository urlRepository;

	@Transactional
	public UrlResponse encodeUrl(ShortUrlCreateRequest request) {
		checkDuplicate(request);

		Url url = request.toEntity();
		Url savedUrl = urlRepository.save(url);

		String encodedUrl = encoder.encode(request);
		String shortUrl = makeShortUrl(encodedUrl);

		savedUrl.enrollEncodedUrl(shortUrl);

		UrlResponse response = UrlResponse.from(savedUrl);

		return response;
	}

	@Transactional(readOnly = true)
	public OriginUrlResponse getOriginUrl(OriginUrlRequest request) {
		Url url = urlRepository.findByEncodedUrl(request.shortUrl())
			.orElseThrow(() -> new RuntimeException("존재하지 않는 shortUrl입니다."));

		OriginUrlResponse response = OriginUrlResponse.from(url);

		return response;
	}

	@Transactional
	public OriginUrlResponse getOriginUrl(String baseUrl, String encodedUrl) {
		checkBaseUrl(baseUrl);
		String shortUrl = makeShortUrl(encodedUrl);
		Url url = urlRepository.findByEncodedUrl(shortUrl)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 shortUrl입니다."));
		url.plusHitCount();

		OriginUrlResponse response = OriginUrlResponse.from(url);

		return response;
	}

	private void checkDuplicate(ShortUrlCreateRequest request) {
		if(urlRepository.existsByAlgorithmAndOriginUrl(request.algorithm(), request.originUrl())) {
			throw new RuntimeException("이미 존재하는 url과 알고리즘의 조합입니다.");
		}
	}

	private void checkBaseUrl(String baseUrl) {
		if(!baseUrl.equals(BASE_URL)) {
			throw new RuntimeException("잘못된 BASE URL 요청입니다.");
		}
	}

	private String makeShortUrl(String encodedUrl) {
		return BASE_URL + "/" + encodedUrl;
	}

}
