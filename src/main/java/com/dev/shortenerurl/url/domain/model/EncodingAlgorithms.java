package com.dev.shortenerurl.url.domain.model;

import com.dev.shortenerurl.url.domain.Encoder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncodingAlgorithms {

	BASE_62(new Encoder() {
		@Override
		public String encode(Long id) {
			return null;
		}
	});

	private final Encoder encoder;

	public String encode(Long id) {
		return encoder.encode(id);
	}
}
