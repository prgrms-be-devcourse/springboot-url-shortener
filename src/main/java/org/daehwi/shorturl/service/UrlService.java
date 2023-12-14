package org.daehwi.shorturl.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.daehwi.shorturl.controller.dto.ResponseStatus;
import org.daehwi.shorturl.controller.dto.ShortUrlRequest;
import org.daehwi.shorturl.domain.ShortUrl;
import org.daehwi.shorturl.exception.CustomException;
import org.daehwi.shorturl.repository.UrlRepository;
import org.daehwi.shorturl.util.UrlValidator;
import org.daehwi.shorturl.util.encoder.Base62Encoder;
import org.daehwi.shorturl.util.encoder.Encoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlService {

    private static final int DEFAULT_HASH_SIZE = 8;

    private final UrlRepository urlRepository;

    public String createShortUrl(ShortUrlRequest requestDto) {
        Encoder encoder = new Base62Encoder();

        final String cleanUrl = UrlValidator.validateUrl(requestDto.url());
        final BigInteger id = getUniqueId(cleanUrl);
        String shortUrl = encoder.encode(id);
        urlRepository.save(new ShortUrl(id.longValue(), cleanUrl, shortUrl));
        return shortUrl;
    }

    public String getOriginUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new CustomException(ResponseStatus.SHORT_URL_NOT_FOUND))
                .getOriginUrl();
    }

    private BigInteger getUniqueId(String originUrl) {
        Encoder encoder = new Base62Encoder();

        int extra = 0;
        while (true) {
            final BigInteger id = encoder.sha256Hash(originUrl, DEFAULT_HASH_SIZE + extra);
            final Optional<ShortUrl> shortUrl = urlRepository.findById(id.longValue());
            if (shortUrl.isEmpty() || shortUrl.get().getOriginUrl().equals(originUrl)) {
                return id;
            }
            ++extra;
        }
    }
}
