package shortener.infrastructure;

import org.springframework.stereotype.Component;

import shortener.domain.ShortUrl;
import shortener.domain.UrlEncoder;

@Component
public class OtherUrlEncoder implements UrlEncoder {

	@Override
	public String encode(ShortUrl shortUrl, String url) {
		return null;
	}
}
