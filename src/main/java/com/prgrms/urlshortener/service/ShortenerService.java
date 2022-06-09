package com.prgrms.urlshortener.service;

import com.prgrms.urlshortener.common.error.exception.NonExistedUrlException;
import com.prgrms.urlshortener.domain.url.Url;
import com.prgrms.urlshortener.domain.url.UrlRepository;
import com.prgrms.urlshortener.dto.ShortenUrlResponse;
import com.prgrms.urlshortener.dto.UrlResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.urlshortener.common.util.Base62.decoding;
import static com.prgrms.urlshortener.common.util.Validator.validateEncodedId;
import static com.prgrms.urlshortener.common.util.Validator.validateUrl;

@Service
public class ShortenerService {
    private final UrlRepository urlRepository;

    public ShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public ShortenUrlResponse createShortenUrl(String requestedUrl) {
        validateUrl(requestedUrl);

        Url url = urlRepository.findByUrl(requestedUrl)
                .orElseGet(() -> urlRepository.save(new Url(requestedUrl, 0)));

        return url.getShortenUrlResponse();
    }

    @Transactional
    public UrlResponse getOriginalUrl(String encodedId) {
        validateEncodedId(encodedId);

        Long id = decoding(encodedId);

        Url url = urlRepository.findById(id).orElseThrow(() -> new NonExistedUrlException());
        url.increaseRequestNumber();

        return url.getUrlResponse();
    }
}
