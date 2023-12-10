package com.dev.shortenerurl.url.domain.model;

import com.dev.shortenerurl.url.domain.Encoder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EncodingAlgorithms {

	//인코딩 알고리즘은 추후에 구현하기 위해 임시로 7문자를 리턴하도록 구현
	BASE_62(new Encoder() {
		@Override
		public String encode(Long id) {
			return "AAAAAAA";
		}
	});

	private final Encoder encoder;

	public String encode(Long id) {
		return encoder.encode(id);
	}
}
