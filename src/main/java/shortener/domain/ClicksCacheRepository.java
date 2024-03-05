package shortener.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClicksCacheRepository {
	ShortUrl save(ShortUrl shortUrl);

	void updateClicks(String encodedUrl);

	Optional<Long> findClicksByEncodedUrl(String encodedUrl);

	Map<Long, Long> findAll(List<ShortUrl> shortUrls);
}
