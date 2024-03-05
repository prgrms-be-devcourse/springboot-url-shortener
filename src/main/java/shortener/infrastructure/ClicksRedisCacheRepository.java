package shortener.infrastructure;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import shortener.domain.ClicksCacheRepository;
import shortener.domain.ShortUrl;
import shortener.global.error.ErrorCode;
import shortener.global.error.exception.CacheNotFoundException;

@Slf4j
@Repository
public class ClicksRedisCacheRepository implements ClicksCacheRepository {

	private final ValueOperations<String, Long> valueOperations;

	public ClicksRedisCacheRepository(RedisTemplate<String, Long> redisTemplateForClicks) {
		this.valueOperations = redisTemplateForClicks.opsForValue();
	}

	@Override
	public ShortUrl save(ShortUrl shortUrl) {
		String encodedUrl = shortUrl.getEncodedUrl();
		long clicks = shortUrl.getClicks();

		log.info("Trying to save clicks(key({}), value({}) into cache repository...", encodedUrl, clicks);
		valueOperations.set(encodedUrl, clicks);
		log.info("Success to save into cache.");

		return shortUrl;
	}

	@Override
	public void updateClicks(String encodedUrl) {
		log.info("Trying to find clicks in cache...");
		Long clicks = Optional.ofNullable(valueOperations.get(encodedUrl))
			.orElseThrow(() -> new CacheNotFoundException(ErrorCode.NOT_FOUND_MAPPED_URL));
		log.info("Success to find clicks(value({})) from cache.", clicks);

		log.info("Trying to update clicks({}) in cache...", clicks);
		valueOperations.set(encodedUrl, clicks + 1);
		log.info("Success to update clicks(value({})) in cache.", clicks + 1);
	}

	@Override
	public Optional<Long> findClicksByEncodedUrl(String encodedUrl) {
		log.info("Get clicks from cache...");
		Long clicks = valueOperations.get(encodedUrl);

		return Optional.ofNullable(clicks);
	}

	@Override
	public Map<Long, Long> findAll(List<ShortUrl> shortUrls) {
		log.info("Trying to map shortUrl id to clicks from cache...");
		return shortUrls.stream()
			.collect(Collectors.toConcurrentMap(shortUrl -> {
				Long id = shortUrl.getId();
				log.info("key(id): {}", id);

				return id;
			}, shortUrl -> {
				String encodedUrl = shortUrl.getEncodedUrl();
				Long clicks = Optional.ofNullable(valueOperations.get(encodedUrl))
					.orElseThrow(() -> new RuntimeException(
						MessageFormat.format("Can not find clicks for key({0})", encodedUrl)
					));
				log.info("value(clicks): {}", clicks);

				return clicks;
			}));
	}
}
