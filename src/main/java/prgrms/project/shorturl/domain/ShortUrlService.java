package prgrms.project.shorturl.domain;

import static java.text.MessageFormat.*;
import static prgrms.project.shorturl.global.ShortUrlConverter.*;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prgrms.project.shorturl.dto.ShortUrlCreateRequest;
import prgrms.project.shorturl.dto.ShortUrlRedirectResponse;
import prgrms.project.shorturl.dto.ShortUrlResponse;
import prgrms.project.shorturl.global.algorithm.ShortUrlGenerator;
import prgrms.project.shorturl.global.exception.EntityNotFoundException;

@Service
public class ShortUrlService {

	private final ShortUrlRepository shortUrlRepository;
	private final Map<String, ShortUrlGenerator> shortUrlGenerators;

	public ShortUrlService(
		ShortUrlRepository shortUrlRepository,
		Map<String, ShortUrlGenerator> shortUrlGenerators
	) {
		this.shortUrlRepository = shortUrlRepository;
		this.shortUrlGenerators = shortUrlGenerators;
	}

	@Transactional
	public ShortUrlResponse createShortUrl(ShortUrlCreateRequest createRequest) {
		var shortUrlGenerator = shortUrlGenerators.get(createRequest.method());
		var shortUrl = ShortUrl.createShortUrl(createRequest.originUrl(), shortUrlGenerator.generate());

		return shortUrlResponseFrom(shortUrlRepository.save(shortUrl));
	}

	@Transactional
	public ShortUrlRedirectResponse increaseRequestCount(String shortUrl) {
		var retrievedShortUrl = shortUrlRepository
			.findByShortenUrl(shortUrl)
			.orElseThrow(
				() ->
					new EntityNotFoundException(format("해당 shorUrl 을 찾지 못했습니다. [shortUrl: {0}]", shortUrl))
			);

		retrievedShortUrl.increaseNumberOfRequests();

		return shortUrlRedirectResponseFrom(retrievedShortUrl);
	}
}
