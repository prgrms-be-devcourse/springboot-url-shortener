package com.dev.shortenerurl.url.domain.encoder;

public class Base62Encoder extends Encoder {

	private static final char[] TOKENS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	private static final int TOKENS_LENGTH = 62;

	private void addPadding(StringBuilder encodingBuilder) {
		while (encodingBuilder.length() < MAX_LENGTH) {
			encodingBuilder.append("A");
		}
	}

	private void validateLengthEqualMaxLength(String encodedString) {
		if (encodedString.length() != MAX_LENGTH) {
			throw new IllegalArgumentException("인코딩 결과가 최대 길이랑 다릅니다");
		}
	}

	@Override
	public String encode(Long id) {
		StringBuilder encodingBuilder = new StringBuilder();

		do {
			int tokenIndex = (int)(id % TOKENS_LENGTH);
			encodingBuilder.append(TOKENS[tokenIndex]);

			id /= TOKENS_LENGTH;
		} while (id > 0);

		addPadding(encodingBuilder);
		String encodedString = encodingBuilder.toString();
		validateLengthEqualMaxLength(encodedString);

		return encodedString;
	}
}
