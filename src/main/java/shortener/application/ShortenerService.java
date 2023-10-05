package shortener.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
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
	private final ShortUrlCacheRepository clicksCacheRepository;
	private final UrlEncoder urlEncoder;

	public ShortenerService(
		ShortUrlJpaRepository shortUrlRepository,
		@Qualifier("originalUrlCacheRepository")
		ShortUrlCacheRepository originalUrlCacheRepository,
		@Qualifier("clicksCacheRepository")
		ShortUrlCacheRepository clicksCacheRepository,
		UrlEncoder urlEncoder
	) {
		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlCacheRepository = originalUrlCacheRepository;
		this.clicksCacheRepository = clicksCacheRepository;
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
		// // 1. 캐시 저장소에서 먼저 조회 처리
		// Optional<String> cachedOriginalUrl = shortUrlCacheRepository.findOriginalUrlByEncodedUrl(encodedUrl);
		// if (cachedOriginalUrl.isPresent()) {
		//
		// 	return ;
		// }
		//
		// // 2. 캐시 저장소에서 처리할 수 없다면 RDB에서 처리
		// // 이때는 레디스 서버가 불량이기 때문에 fallback으로 temporary_clicks에 조회수를 카운트
		//
		// shortenerService.findOriginalUrl(encodedUrl);

		return null;
	}

	private String findOriginalUrlByNoCache(String encodedUrl) {
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
