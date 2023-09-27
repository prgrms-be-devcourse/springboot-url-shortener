package org.prgrms.urlshortener.application;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.util.encoder.Base62Encoder;
import org.prgrms.urlshortener.util.encoder.Encoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncoderFactory {

	public static Encoder createEncoder(Algorithm algorithm) {
		switch (algorithm) {
			case BASE_62 -> {
				return new Base62Encoder();
			}
		}

		throw new RuntimeException("존재하지 않는 알고리즘입니다.");
	}
}
