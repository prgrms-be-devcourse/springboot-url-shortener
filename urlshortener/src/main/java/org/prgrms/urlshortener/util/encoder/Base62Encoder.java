package org.prgrms.urlshortener.util.encoder;

public class Base62Encoder implements Encoder {

	private static final String baseCharacters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final char[] baseCharacterList = baseCharacters.toCharArray();

	private static final int BASE = baseCharacterList.length;

	@Override
	public String encode(String toEncodeUrl, Long id) {
		StringBuilder stringBuilder = new StringBuilder();
		int intId = id.intValue();

		while(id > 0) {
			int index = intId % BASE;
			stringBuilder.append(baseCharacterList[index]);
			id /= BASE;
		}

		String encodedUrl = stringBuilder.toString();
		return encodedUrl;
	}

}
