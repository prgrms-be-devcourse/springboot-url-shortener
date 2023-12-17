package devcourse.springbooturlshortener.service;

import devcourse.springbooturlshortener.dto.ShortUrlCreateRequest;
import devcourse.springbooturlshortener.entity.Url;
import devcourse.springbooturlshortener.entity.vo.OriginalUrl;
import devcourse.springbooturlshortener.repository.UrlRepository;
import devcourse.springbooturlshortener.urlalgorithm.Base62EncodeUrlAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62EncodeUrlAlgorithm base62EncodeUrlAlgorithm;

    @Transactional
    public String getOriginalUrlAndIncreaseHit(String shortUrl) {
        Url url = this.getUrlByShortUrl(shortUrl);
        increaseHitCount(url);

        return url.getOriginalUrl().getValue();
    }

    @Transactional
    public String createShortUrl(ShortUrlCreateRequest request) {
        Url url = getOrCreateUrlByOriginalUrl(new OriginalUrl(request.originalUrl()));
        increaseHitCount(url);

        return url.getShortUrl();
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
