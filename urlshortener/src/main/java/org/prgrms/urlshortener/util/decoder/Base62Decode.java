package org.prgrms.urlshortener.util.decoder;

import org.prgrms.urlshortener.domain.Url;
import org.prgrms.urlshortener.respository.UrlRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Base62Decode implements DecodePolicy{

	private final UrlRepository urlRepository;

	@Override
	public String decode(String encodedUrl) {
		Url url = urlRepository.findByEncodedUrl(encodedUrl)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 encodedUrl 입니다."));

		String originUrl = url.getOriginUrl();
		return originUrl;
	}
}
