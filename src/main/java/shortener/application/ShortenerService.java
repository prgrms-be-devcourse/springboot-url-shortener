package shortener.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import shortener.application.dto.response.ClicksResponse;
import shortener.application.dto.response.ShortUrlCreateResponse;
import shortener.domain.ClicksCacheRepository;
import shortener.domain.OriginalUrlCacheRepository;
import shortener.domain.ShortUrl;
import shortener.domain.UrlEncoder;
import shortener.global.error.ErrorCode;
import shortener.global.error.exception.EntityNotFoundException;
import shortener.infrastructure.ShortUrlJpaRepository;

// todo: 해결하지 못한 점 : 레디스 연결 자체에 문제가 생길 경우 예외처리를 하는 방법..? -> 컨트롤러에서 잡아라..!
// todo: 왜 스케줄러 업데이트가 안되는지..? -> 1순위
// todo: 왜 리다이렉션 이후에 favicon.ico도 요청으로 들어오는지? -> 2순위
@Slf4j
@Transactional
@Service
public class ShortenerService {

	private final ShortUrlJpaRepository shortUrlRepository;
	private final OriginalUrlCacheRepository originalUrlCacheRepository;
	private final ClicksCacheRepository clicksCacheRepository;
	private final UrlEncoder urlEncoder;

	public ShortenerService(
		ShortUrlJpaRepository shortUrlRepository,
		@Qualifier("originalUrlRedisCacheRepository")
		OriginalUrlCacheRepository originalUrlCacheRepository,
		@Qualifier("clicksRedisCacheRepository")
		ClicksCacheRepository clicksCacheRepository,
		UrlEncoder urlEncoder
	) {
		this.shortUrlRepository = shortUrlRepository;
		this.originalUrlCacheRepository = originalUrlCacheRepository;
		this.clicksCacheRepository = clicksCacheRepository;
		this.urlEncoder = urlEncoder;
	}

	public ShortUrlCreateResponse createShortUrl(String originalUrl) {
		log.info("Start creating new shortUrl Entity...");
		ShortUrl newShortUrl = new ShortUrl(originalUrl);
		ShortUrl savedShortUrl = shortUrlRepository.save(newShortUrl);
		log.info("Success to create new Entity(id({}))", savedShortUrl.getId());

		log.info("Start encoding new shortUrl...");
		String encodedUrl = urlEncoder.encode(savedShortUrl, originalUrl);

		log.info("Start saving new shortUrl({}) in master database...", encodedUrl);
		savedShortUrl.updateEncodedUrl(encodedUrl);
		log.info("Success to save new shortUrl");

		saveOriginalUrlAndClicksInCache(newShortUrl);

		return ShortUrlCreateResponse.of(savedShortUrl);
	}

	public String findOriginalUrl(String encodedUrl) {
		log.info("Trying to find originalUrl from cache...");
		Optional<String> cachedOriginalUrl = originalUrlCacheRepository.findOriginalUrlByEncodedUrl(encodedUrl);
		if (cachedOriginalUrl.isPresent()) {
			String originalUrl = cachedOriginalUrl.get();
			log.info("Success to find originalUrl({}) in cache.", originalUrl);
			updateClicksInCache(encodedUrl);

			return originalUrl;
		}
		log.warn("Fail to find originalUrl from cache!!!");

		log.info("Switch to master database system");
		String originalUrl = findOriginalUrlByNoCache(encodedUrl);

		log.info("Trying to save shortUrl(key({}), value({})) in cache...", encodedUrl, originalUrl);
		ShortUrl shortUrl = shortUrlRepository.findShortUrlByEncodedUrl(encodedUrl)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MAPPED_URL));
		saveOriginalUrlAndClicksInCache(shortUrl);
		updateClicksInCache(encodedUrl);

		return originalUrl;
	}

	public ClicksResponse findClicks(String encodedUrl) {
		log.info("Trying to find clicks for encodedUrl({}) from cache...", encodedUrl);
		Optional<Long> clicksFromCache = clicksCacheRepository.findClicksByEncodedUrl(encodedUrl);
		if (clicksFromCache.isPresent()) {
			Long clicks = clicksFromCache.get();
			log.info("Success to find clicks(encodedUrl({}), clicks({})) from cache.", encodedUrl, clicks);

			return ClicksResponse.of(encodedUrl, clicks);
		}
		log.warn("Fail to find clicks from cache!!!");

		log.info("Switch to master database system");
		log.info("Trying to find clicks for encodedUrl({}) from master database...", encodedUrl);
		ShortUrl shortUrl = shortUrlRepository.findShortUrlByEncodedUrl(encodedUrl)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MAPPED_URL));
		long clicks = shortUrl.getClicks();
		log.info("Success to find clicks(encodedUrl({}), clicks({})) from master database.", encodedUrl, clicks);

		saveOriginalUrlAndClicksInCache(shortUrl);

		return ClicksResponse.of(encodedUrl, clicks);
	}

	private void saveOriginalUrlAndClicksInCache(ShortUrl shortUrl) {
		log.info("Trying to save shortUrl in cache...");
		originalUrlCacheRepository.save(shortUrl);
		clicksCacheRepository.save(shortUrl);
		log.info("Success to save shortUrl in cache.");
	}

	private String findOriginalUrlByNoCache(String encodedUrl) {
		log.info("Trying to find originalUrl by encodedUrl({})...", encodedUrl);
		ShortUrl foundShortUrl = shortUrlRepository.findShortUrlByEncodedUrl(encodedUrl)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_MAPPED_URL));
		String originalUrl = foundShortUrl.getOriginalUrl();
		log.info("Success to find originalUrl({})", originalUrl);

		return originalUrl;
	}

	private void updateClicksInCache(String encodedUrl) {
		log.info("Update clicks in cache...");
		clicksCacheRepository.updateClicks(encodedUrl);
		log.info("Success to update clicks in cache.");
	}
}
