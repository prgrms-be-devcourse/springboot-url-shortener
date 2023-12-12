package com.prgrms.shorturl.service;

import com.prgrms.shorturl.domain.EncodingFactory;
import com.prgrms.shorturl.domain.ShortUrl;
import com.prgrms.shorturl.dto.ShortUrlRequest;
import com.prgrms.shorturl.dto.ShortUrlResponse;
import com.prgrms.shorturl.exception.NoSuchOriginalUrlException;
import com.prgrms.shorturl.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;

    public ShortUrl save(String originalUrl) {
        originalUrl = originalUrl.replaceAll("^(http://|https://)", "");
        String base62Url = EncodingFactory.toBase62(originalUrl);
        return shortUrlRepository.save(new ShortUrl(originalUrl, base62Url));
    }

    @Transactional
    public ShortUrlResponse getByOriginalUrl(ShortUrlRequest req) {
        Optional<ShortUrl> find = shortUrlRepository.findByOriginalUrl(req.originalUrl());
        if(find.isEmpty()) {
            return toShortUrlResponse(save(req.originalUrl()));
        }
        return toShortUrlResponse(find.get());
    }

    @Transactional(readOnly = true)
    public String getByShortUrl(String shortUrl) {
        ShortUrl find = shortUrlRepository.findByBase62Url(shortUrl)
                .orElseThrow(() -> new NoSuchOriginalUrlException("매칭되는 주소가 없습니다."));
        return find.getBase62Url();
    }

    private ShortUrlResponse toShortUrlResponse(ShortUrl shortUrl) {
        return ShortUrlResponse.builder()
                .id(shortUrl.getId())
                .originalUrl(shortUrl.getOriginalUrl())
                .base62Url(shortUrl.getBase62Url())
                .build();
    }
}
