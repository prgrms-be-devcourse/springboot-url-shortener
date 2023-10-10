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
import shortener.global.error.ErrorCode;
import shortener.global.error.exception.EntityNotFoundException;
import shortener.infrastructure.ShortUrlJpaRepository;
import shortener.domain.urlencoder.UrlEncoder;

@Slf4j
@Transactional
@Service
public class ShortenerService {

	private final ShortUrlJpaRepository shortUrlRepository;
	private final OriginalUrlCacheRepository originalUrlCacheRepository;
	private final ClicksCacheRepository clicksCacheRepository;

	public ShortenerService(
		ShortUrlJpaRepository shortUrlRepository,
		@Qualifier("originalUrlRedisCacheRepository")
		OriginalUrlCacheRepository originalUrlCacheRepository,
		@Qualifier("clicksRedisCacheRepository")
		ClicksCacheRepository clicksCacheRepository
	) {
		this.shortUrlRepository = shortUrlRepository;
		this.originalUrlCacheRepository = originalUrlCacheRepository;
		this.clicksCacheRepository = clicksCacheRepository;
	}

	public ShortUrlCreateResponse saveNewShortUrl(String originalUrl, int algorithmId) {
		ShortUrl newShortUrl = saveShortUrlInMaster(originalUrl, algorithmId);
		saveOriginalUrlAndClicksInCache(newShortUrl);

		return ShortUrlCreateResponse.of(newShortUrl);
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

	private ShortUrl saveShortUrlInMaster(String originalUrl, int algorithmId) {
		log.info("Start creating new shortUrl Entity...");
		ShortUrl newShortUrl = new ShortUrl(originalUrl);
		ShortUrl savedShortUrl = shortUrlRepository.save(newShortUrl);
		log.info("Success to create new Entity(id({}))", savedShortUrl.getId());

		log.info("Start encoding new shortUrl...");
		String encodedUrl = UrlEncoder.getShortUrl(savedShortUrl, algorithmId);

		log.info("Start saving new shortUrl({}) in master database...", encodedUrl);
		savedShortUrl.updateEncodedUrl(encodedUrl);
		log.info("Success to save new shortUrl");

		return savedShortUrl;
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
