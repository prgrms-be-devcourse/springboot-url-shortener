package prgrms.project.shorturl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgrms.project.shorturl.domain.ShortUrl;
import prgrms.project.shorturl.dto.ShortUrlCreateRequest;
import prgrms.project.shorturl.dto.ShortUrlRedirectResponse;
import prgrms.project.shorturl.dto.ShortUrlResponse;
import prgrms.project.shorturl.global.algorithm.AlgorithmAdapter;
import prgrms.project.shorturl.global.exception.EntityNotFoundException;
import prgrms.project.shorturl.repository.ShortUrlRepository;

import static java.text.MessageFormat.format;
import static prgrms.project.shorturl.global.converter.ShortUrlConverter.shortUrlRedirectResponseFrom;
import static prgrms.project.shorturl.global.converter.ShortUrlConverter.shortUrlResponseFrom;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final static String INIT_SHORT_URL = "INIT";

    @Transactional
    public ShortUrlResponse createShortUrl(ShortUrlCreateRequest createRequest) {
        var algorithm = AlgorithmAdapter.valueOf(createRequest.algorithm().toUpperCase());
        var savedShortUrl = shortUrlRepository.save(new ShortUrl(createRequest.originUrl(), INIT_SHORT_URL));

        var shortUrl = algorithm.encode(savedShortUrl.getId());
        savedShortUrl.assignShortUrl(shortUrl);

        return shortUrlResponseFrom(savedShortUrl);
    }

    @Transactional
    public ShortUrlRedirectResponse increaseRequestCount(String shortUrl) {
        var retrievedShortUrl = shortUrlRepository
            .findByShortUrl(shortUrl)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(format("해당 shorUrl 을 찾지 못했습니다. [shortUrl: {0}]", shortUrl))
            );

        retrievedShortUrl.increaseRequestCount();

        return shortUrlRedirectResponseFrom(retrievedShortUrl);
    }
}
