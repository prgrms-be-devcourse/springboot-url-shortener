package shortener.domain;

import java.util.Optional;

public interface ShortUrlCacheRepository {
	ShortUrl save(ShortUrl shortUrl);

	Optional<String> findOriginalUrlByEncodedUrl(String encodedUrl);
}
