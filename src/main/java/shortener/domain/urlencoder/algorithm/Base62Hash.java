package shortener.domain.urlencoder.algorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Base62Hash {

	private static final char[] base62CryptoWords = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
		'U', 'V', 'W', 'X', 'Y', 'Z',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
		'u', 'v', 'w', 'x', 'y', 'z'
	};

	public String encode(long index) {
		StringBuilder encodedUrlBuilder = new StringBuilder();
		log.info("Start encoding shortUrl by id({})", index);
		do {
			int cryptoIndex = (int)(index % 62);
			encodedUrlBuilder.append(base62CryptoWords[cryptoIndex]);
			index /= 62;
		} while (index % 62 > 0);
		log.info("Success to encode to shortUrl({})", encodedUrlBuilder);

		return encodedUrlBuilder.toString();
	}
}
