package org.prgrms.urlshortener.application;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.respository.UrlRepository;
import org.prgrms.urlshortener.util.decoder.Base62Decode;
import org.prgrms.urlshortener.util.decoder.DecodePolicy;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DecodePolicyFactory {

	private final UrlRepository urlRepository;

	public DecodePolicy createDecodePolicy(Algorithm algorithm) {
		switch (algorithm) {
			case BASE_62 -> {
				return new Base62Decode(urlRepository);
			}
		}

		throw new RuntimeException("존재하지 않는 알고리즘입니다.");
	}

}
