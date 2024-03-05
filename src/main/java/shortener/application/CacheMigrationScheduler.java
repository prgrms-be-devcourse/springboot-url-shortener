package shortener.application;

import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import shortener.domain.ClicksCacheRepository;
import shortener.domain.ShortUrl;
import shortener.infrastructure.ShortUrlJpaRepository;

@Slf4j
@Transactional
@Service
public class CacheMigrationScheduler {

	private final ShortUrlJpaRepository shortUrlRepository;
	private final ClicksCacheRepository clicksCacheRepository;

	public CacheMigrationScheduler(
		ShortUrlJpaRepository shortUrlRepository,
		ClicksCacheRepository clicksCacheRepository
	) {
		this.shortUrlRepository = shortUrlRepository;
		this.clicksCacheRepository = clicksCacheRepository;
	}

	@Scheduled(cron = "0 0 3 * * *")
	public void migrateClicksCacheDataToMasterDatabase() {
		log.info("Run scheduler to migrate clicks from cache to master database...");
		log.info("Find all shortUrl from master database...");
		List<ShortUrl> savedShortUrls = shortUrlRepository.findAll();
		log.info("Success to find all shortUrl.");
		Map<Long, Long> clicksForMigration = clicksCacheRepository.findAll(savedShortUrls);
		clicksForMigration.forEach((id, clicks) -> {
			log.info("Trying to update id({}) clicks({})...", id, clicks);
			shortUrlRepository.updateClicks(id, clicks);
			log.info("Success to update");
		});
	}
}
