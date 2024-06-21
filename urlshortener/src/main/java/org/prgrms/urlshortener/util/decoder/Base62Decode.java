package org.prgrms.urlshortener.util.decoder;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;
import org.prgrms.urlshortener.respository.UrlRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Base62Decode implements DecodePolicy{

	private final UrlRepository urlRepository;

	@Override
	public String decode(String encodedUrl) {
		Url url = urlRepository.findByAlgorithmAndEncodedUrl(Algorithm.BASE_62, encodedUrl)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 encodedUrl 입니다."));

		String originUrl = url.getOriginUrl();
		return originUrl;
	}
}
