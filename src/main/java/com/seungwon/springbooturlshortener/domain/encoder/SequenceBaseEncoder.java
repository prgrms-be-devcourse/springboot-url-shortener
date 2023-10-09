package com.seungwon.springbooturlshortener.domain.encoder;

public class SequenceBaseEncoder implements Encoder {
	@Override
	public String encode(long originalValue) {
		return String.valueOf(originalValue + 1);
	}
}
