package com.programmers.urlshortener.service;

import com.programmers.urlshortener.domain.ShortUrl;
import com.programmers.urlshortener.dto.request.CreateShortUrlRequest;
import com.programmers.urlshortener.dto.response.ShortUrlResponse;
import com.programmers.urlshortener.repository.ShortUrlRepository;
import com.programmers.urlshortener.util.encoder.UrlEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShortUrlService {
    private final UrlEncoder encoder;
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlResponse createShortUrl(CreateShortUrlRequest request) {
        Optional<ShortUrl> foundShortUrl = shortUrlRepository.findByOriginUrl(request.url());
        ShortUrl shortUrl = foundShortUrl
                .orElseGet(() -> shortUrlRepository.save(new ShortUrl(encoder, request.url())));

        return ShortUrlResponse.from(shortUrl);
    }

    public String getOriginUrl(String urlKey) {
        ShortUrl shortUrl = shortUrlRepository.findByUrlKey(urlKey)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."));

        shortUrl.increaseRequestCount();

        return shortUrl.getOriginUrl();
    }
}
