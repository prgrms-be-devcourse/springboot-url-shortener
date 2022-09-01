package prgrms.project.shorturl.service;

import static java.text.MessageFormat.*;
import static prgrms.project.shorturl.global.converter.ShortUrlConverter.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import prgrms.project.shorturl.domain.ShortUrl;
import prgrms.project.shorturl.dto.ShortUrlCreateRequest;
import prgrms.project.shorturl.dto.ShortUrlRedirectResponse;
import prgrms.project.shorturl.dto.ShortUrlResponse;
import prgrms.project.shorturl.global.algorithm.AlgorithmAdapter;
import prgrms.project.shorturl.global.exception.EntityNotFoundException;
import prgrms.project.shorturl.repository.ShortUrlRepository;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final static String INIT_SHORT_URL = "INIT";

    public ShortUrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Transactional
    public ShortUrlResponse createShortUrl(ShortUrlCreateRequest createRequest) {
        var algorithm = AlgorithmAdapter.valueOf(createRequest.algorithm().toUpperCase());
        var savedShortUrl = shortUrlRepository.save(new ShortUrl(createRequest.originUrl(), INIT_SHORT_URL));

        var shortUrl = algorithm.encode(savedShortUrl.getId());

        return shortUrlResponseFrom(savedShortUrl);
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
