package shortener.infrastructure;

import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import shortener.domain.ShortUrl;
import shortener.domain.ShortUrlCacheRepository;

@Slf4j
@Repository
public class ClicksCacheRepository implements ShortUrlCacheRepository {

	private final ValueOperations<String, String> valueOperations;

	public ClicksCacheRepository(RedisTemplate<String, String> redisTemplateForClicks) {
		this.valueOperations = redisTemplateForClicks.opsForValue();
	}

	@Override
	public ShortUrl save(ShortUrl shortUrl) {
		return null;
	}

	@Override
	public Optional<String> findOriginalUrlByEncodedUrl(String encodedUrl) {
		return Optional.empty();
	}
}
