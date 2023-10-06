package shortener.infrastructure;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import shortener.domain.ShortUrl;
import shortener.domain.UrlEncoder;
import shortener.config.UrlEncoderProperty;

@Slf4j
@Primary
@Component
public class Base62UrlEncoder implements UrlEncoder {
	private final char[] base62CryptoWords;

	public Base62UrlEncoder(UrlEncoderProperty urlEncoderProperty) {
		this.base62CryptoWords = urlEncoderProperty.getBase62CryptoWords();
	}

	@Override
	public String encode(ShortUrl shortUrl, String url) {
		StringBuilder encodedUrlBuilder = new StringBuilder();
		int index = shortUrl.getId().intValue();

		log.info("Start encoding shortUrl by id({})", index);
		do {
			encodedUrlBuilder.append(base62CryptoWords[index % 62]);
			index /= 62;
		} while (index % 62 > 0);
		log.info("Success to encode to shortUrl({})", encodedUrlBuilder);

		return encodedUrlBuilder.toString();
	}
}
