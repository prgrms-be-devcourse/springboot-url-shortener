package com.programmers.urlshortener.service;

import com.programmers.urlshortener.domain.ShortUrl;
import com.programmers.urlshortener.dto.request.CreateShortUrlRequest;
import com.programmers.urlshortener.dto.response.ShortUrlResponse;
import com.programmers.urlshortener.repository.ShortUrlRepository;
import com.programmers.urlshortener.util.encoder.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlResponse createShortUrl(CreateShortUrlRequest request) {
        String originUrl = request.url();
        Algorithm algorithm = Algorithm.from(request.algorithm());

        Optional<ShortUrl> foundShortUrl = shortUrlRepository.findByOriginUrlAndAlgorithm(originUrl, algorithm);
        ShortUrl shortUrl = foundShortUrl
                .orElseGet(() -> shortUrlRepository.save(new ShortUrl(Algorithm.getUrlEncoder(algorithm), originUrl)));

        return ShortUrlResponse.from(shortUrl);
    }

    public String getOriginUrl(String urlKey) {
        ShortUrl shortUrl = shortUrlRepository.findByUrlKey(urlKey)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 URL입니다."));

        shortUrl.increaseRequestCount();

        return shortUrl.getOriginUrl();
    }
}
