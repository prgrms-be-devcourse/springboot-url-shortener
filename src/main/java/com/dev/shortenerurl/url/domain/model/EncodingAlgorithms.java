package com.dev.shortenerurl.url.domain.model;

import com.dev.shortenerurl.url.domain.encoder.Base62Encoder;
import com.dev.shortenerurl.url.domain.encoder.Encoder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncodingAlgorithms {

	BASE_62(new Base62Encoder());

	private final Encoder encoder;

	public String encode(Long id) {
		return encoder.encode(id);
	}
}
