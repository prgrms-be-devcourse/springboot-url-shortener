package devcourse.springbooturlshortener.service;

import devcourse.springbooturlshortener.dto.ShortUrlCreateRequest;
import devcourse.springbooturlshortener.dto.ShortUrlFindResponse;
import devcourse.springbooturlshortener.entity.Url;
import devcourse.springbooturlshortener.entity.vo.OriginalUrl;
import devcourse.springbooturlshortener.repository.UrlRepository;
import devcourse.springbooturlshortener.urlalgorithm.UrlAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlAlgorithm base62EncodeUrlAlgorithm;

    @Transactional
    public String getOriginalUrlAndIncreaseHit(String shortUrl) {
        Url url = this.getUrlByShortUrl(shortUrl);
        increaseHitCount(url);

        return url.getOriginalUrl().getValue();
    }

    @Transactional
    public ShortUrlFindResponse createShortUrl(ShortUrlCreateRequest request) {
        Url url = getOrCreateUrlByOriginalUrl(new OriginalUrl(request.originalUrl()));

        return new ShortUrlFindResponse(
                url.getShortUrl(),
                url.getHit()
        );
    }

    private Url getOrCreateUrlByOriginalUrl(OriginalUrl originalUrl) {
        return this.urlRepository.findByOriginalUrl(originalUrl)
                .orElseGet(() -> {
                    Url savedUrl = this.urlRepository.save(
                            Url.builder()
                                    .originalUrl(originalUrl)
                                    .build()
                    );

                    return savedUrl
                            .updateShortUrl(this.base62EncodeUrlAlgorithm.urlEncoder(savedUrl.getId()));
                });
    }

    private Url getUrlByShortUrl(String shortUrl) {
        return this.urlRepository
                .findByShortUrl(shortUrl)
                .orElseThrow(() -> new IllegalArgumentException("Invalid short url"));
    }

    private void increaseHitCount(Url url) {
        this.urlRepository.increaseHit(url.getId());
    }
}
