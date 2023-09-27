package org.prgrms.urlshortener.application;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.util.encoder.Encoder;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmInjector {


	public String encode(String originUrl, Long id, Algorithm algorithm) {
		Encoder encoder = EncoderFactory.createEncoder(algorithm);
		String shortUrl = encoder.encode(originUrl, id);

		return shortUrl;
	}

}
