package com.prgrms.urlshortener.service;

import com.prgrms.urlshortener.domain.url.Url;
import com.prgrms.urlshortener.domain.url.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.urlshortener.common.util.UrlValidator.validateUrl;

@Service
public class ShortenerService {
    private final UrlRepository urlRepository;

    public ShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public String createShortenUrl(String requestedUrl) {
        validateUrl(requestedUrl);

        Url url = urlRepository.findByUrl(requestedUrl)
                .orElseGet(() -> urlRepository.save(new Url(requestedUrl, 0)));

        return url.getEncodedId();
    }
}
