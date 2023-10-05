package shortener.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import shortener.application.dto.response.ShortUrlCreateResponse;
import shortener.domain.ShortUrl;
import shortener.domain.ShortUrlCacheRepository;
import shortener.global.error.ErrorCode;
import shortener.global.error.exception.EntityNotFoundException;
import shortener.infrastructure.ShortUrlJpaRepository;
import shortener.domain.UrlEncoder;

@Slf4j
@Transactional
@Service
public class ShortenerService {

	private final ShortUrlJpaRepository shortUrlRepository;
	private final ShortUrlCacheRepository shortUrlCacheRepository;
	private final UrlEncoder urlEncoder;

	public ShortenerService(
		ShortUrlJpaRepository shortUrlRepository,
		ShortUrlCacheRepository shortUrlCacheRepository,
		UrlEncoder urlEncoder
	) {
		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlCacheRepository = shortUrlCacheRepository;
		this.urlEncoder = urlEncoder;
	}

	public ShortUrlCreateResponse createShortUrl(String originalUrl) {
		log.info("Start creating ShortUrl...");
		ShortUrl newShortUrl = new ShortUrl(originalUrl);

		log.info("Start encoding shortUrl...");
		ShortUrl savedShortUrl = shortUrlRepository.save(newShortUrl);
		String encodedUrl = urlEncoder.encode(savedShortUrl, originalUrl);

		log.info("Start saving shortUrl...");
		savedShortUrl.updateEncodedUrl(encodedUrl);
		shortUrlCacheRepository.save(newShortUrl);
		log.info("Success to save shortUrl({})", encodedUrl);

		return ShortUrlCreateResponse.of(savedShortUrl);
	}

	public String findOriginalUrl(String encodedUrl) {
		log.info("Trying to find originalUrl by encodedUrl({})...", encodedUrl);
		ShortUrl foundShortUrl = shortUrlRepository.findShortUrlByEncodedUrl(encodedUrl)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MAPPED_URL));
		String originalUrl = foundShortUrl.getOriginalUrl();
		log.info("Success to find originalUrl({})", originalUrl);

		log.info("Trying to cache shortUrl info(key({}), value({}))...", encodedUrl, originalUrl);
		shortUrlCacheRepository.save(foundShortUrl);
		log.info("Success to cache.");

		return originalUrl;
	}

	// todo: 레디스 clicks 관련 스케줄링 필요
	//
}
