package shortener.infrastructure;

import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import shortener.domain.ShortUrl;
import shortener.domain.ShortUrlCacheRepository;

@Slf4j
@Repository
public class ShortUrlCacheRedisRepository implements ShortUrlCacheRepository {

	private final ValueOperations<String, String> valueOperations;

	public ShortUrlCacheRedisRepository(StringRedisTemplate redisTemplate) {
		this.valueOperations = redisTemplate.opsForValue();
	}

	@Override
	public ShortUrl save(ShortUrl shortUrl) {
		String encodedUrl = shortUrl.getEncodedUrl();
		String originalUrl = shortUrl.getOriginalUrl();

		log.info("Save url info(key({}), value({}) into cache repository...", encodedUrl, originalUrl);
		valueOperations.set(encodedUrl, originalUrl);
		log.info("Success to save into cache repository.");

		return shortUrl;
	}

	@Override
	public Optional<String> findOriginalUrlByEncodedUrl(String encodedUrl) {
		log.info("Get original url from cache repository...");

		return Optional.ofNullable(valueOperations.get(encodedUrl));
	}
}
