package shortener.domain;

import java.util.Optional;

public interface OriginalUrlCacheRepository {
	ShortUrl save(ShortUrl shortUrl);

	Optional<String> findOriginalUrlByEncodedUrl(String encodedUrl);
}
