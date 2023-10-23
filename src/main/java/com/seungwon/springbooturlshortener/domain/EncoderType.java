package com.seungwon.springbooturlshortener.domain;

import java.util.function.Supplier;

import com.seungwon.springbooturlshortener.domain.encoder.Base62encoder;
import com.seungwon.springbooturlshortener.domain.encoder.Encoder;
import com.seungwon.springbooturlshortener.domain.encoder.SequenceBaseEncoder;

public enum EncoderType {
	SEQUENCE(SequenceBaseEncoder::new),
	BASE62(Base62encoder::new);

	private final Supplier<Encoder> encoder;

	EncoderType(Supplier<Encoder> encoder) {
		this.encoder = encoder;
	}

	public static Encoder getEncoder(String strategy) {
		return EncoderType.valueOf(strategy)
			.encoder
			.get();
	}

}
