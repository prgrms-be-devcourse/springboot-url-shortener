package com.prgrms.shorturl.service;

import com.prgrms.shorturl.domain.ShortUrl;
import com.prgrms.shorturl.dto.ShortUrlRequest;
import com.prgrms.shorturl.dto.ShortUrlResponse;
import com.prgrms.shorturl.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlResponse save(ShortUrlRequest req) {
        String base62Url = EncodingFactory.toBase62(req.originalUrl());
        ShortUrl saved = shortUrlRepository.save(new ShortUrl(req.originalUrl(), base62Url));
        return toShortUrlResponse(saved);
    }

    private ShortUrlResponse toShortUrlResponse(ShortUrl shortUrl) {
        return ShortUrlResponse.builder()
                .id(shortUrl.getId())
                .originalUrl(shortUrl.getOriginalUrl())
                .base62Url(shortUrl.getBase62Url())
                .build();
    }
}
