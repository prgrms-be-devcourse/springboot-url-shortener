package shortener.infrastructure;

import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import shortener.domain.OriginalUrlCacheRepository;
import shortener.domain.ShortUrl;

@Slf4j
@Repository
public class OriginalUrlRedisCacheRepository implements OriginalUrlCacheRepository {

	private final ValueOperations<String, String> valueOperations;

	public OriginalUrlRedisCacheRepository(RedisTemplate<String, String> redisTemplateForOriginalUrl) {
		this.valueOperations = redisTemplateForOriginalUrl.opsForValue();
	}

	@Override
	public ShortUrl save(ShortUrl shortUrl) {
		String encodedUrl = shortUrl.getEncodedUrl();
		String originalUrl = shortUrl.getOriginalUrl();

		log.info("Trying to save originalUrl(key({}), value({}) into cache...", encodedUrl, originalUrl);
		valueOperations.set(encodedUrl, originalUrl);
		log.info("Success to save into cache.");

		return shortUrl;
	}

	@Override
	public Optional<String> findOriginalUrlByEncodedUrl(String encodedUrl) {
		log.info("Get original url from cache...");
		String originalUrl = valueOperations.get(encodedUrl);

		return Optional.ofNullable(originalUrl);
	}
}
