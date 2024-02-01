package org.prgrms.urlshortener.application;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;
import org.prgrms.urlshortener.dto.request.OriginUrlRequest;
import org.prgrms.urlshortener.dto.request.EncodedUrlCreateRequest;
import org.prgrms.urlshortener.dto.response.OriginUrlResponse;
import org.prgrms.urlshortener.dto.response.EncodedUrlCreateResponse;
import org.prgrms.urlshortener.respository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ShortUrlService {

	private static final String BASE_URL = "https://www.bent.ly";

	private final Encoder encoder;

	private final Decoder decoder;

	private final UrlRepository urlRepository;

	@Transactional
	public EncodedUrlCreateResponse encodeUrl(EncodedUrlCreateRequest request) {
		checkDuplicateUrl(request);

		Url url = request.toEntity();
		Url savedUrl = urlRepository.save(url);

		String encodedUrl = encoder.encode(request);
		String shortUrl = makeShortUrl(encodedUrl);

		savedUrl.enrollEncodedUrl(shortUrl);

		EncodedUrlCreateResponse response = EncodedUrlCreateResponse.from(savedUrl);

		return response;
	}

	@Transactional(readOnly = true)
	public OriginUrlResponse getOriginUrl(OriginUrlRequest request) {
		String originUrl = decoder.decode(request.shortUrl(), request.algorithm());
		Url url = urlRepository.findByOriginUrl(originUrl)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 originUrl입니다."));

		OriginUrlResponse response = OriginUrlResponse.from(url);

		return response;
	}

	@Transactional
	public OriginUrlResponse getOriginUrl(String baseUrl, String encodedUrl, Algorithm algorithm) {
		checkBaseUrl(baseUrl);
		String shortUrl = makeShortUrl(encodedUrl);
		String originUrl = decoder.decode(shortUrl, algorithm);

		Url url = urlRepository.findByOriginUrl(originUrl)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 originUrl입니다."));

		url.plusHitCount();

		OriginUrlResponse response = OriginUrlResponse.from(url);

		return response;
	}

	private void checkDuplicateUrl(EncodedUrlCreateRequest request) {
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
