package com.prgrms.urlshortener.domain.shortener.service;

import com.prgrms.urlshortener.domain.shortener.domain.UrlRepository;
import org.springframework.stereotype.Service;

import static com.prgrms.urlshortener.domain.shortener.util.UrlValidator.validateUrl;

@Service
public class ShortenerService {
    private final UrlRepository urlRepository;

    public ShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void getShortenUrl(String url) {
        validateUrl(url);
    }
}
