package shortener.domain;

import java.util.Optional;

public interface ClicksCacheRepository {
	ShortUrl save(ShortUrl shortUrl);

	void updateClicks(String encodedUrl);

	Optional<Long> findClicksByEncodedUrl(String encodedUrl);
}
