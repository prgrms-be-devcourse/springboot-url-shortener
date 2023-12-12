package org.daehwi.shorturl.service;

import lombok.RequiredArgsConstructor;
import org.daehwi.shorturl.controller.dto.UrlRequest;
import org.daehwi.shorturl.repository.UrlRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public String createShortUrl(UrlRequest url) {
        return null;
    }

    public String getOriginUrl(String shortUrl) {
        return null;
    }
}
