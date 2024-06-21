package org.prgrms.urlshortener.application;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.util.decoder.DecodePolicy;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Decoder {

	private final DecodePolicyFactory decodePolicyFactory;

	public String decode(String shortUrl, Algorithm algorithm) {
		DecodePolicy decodePolicy = decodePolicyFactory.createDecodePolicy(algorithm);
		String originUrl = decodePolicy.decode(shortUrl);

		return originUrl;
	}

}
