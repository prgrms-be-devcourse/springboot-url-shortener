package com.dev.shortenerurl.url.domain.encoder;

public abstract class Encoder {

	protected static final int MAX_LENGTH = 7;

	public abstract String encode(Long id);
}
