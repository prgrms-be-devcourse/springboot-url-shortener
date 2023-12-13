package com.prgrms.shorturl.service;

import com.prgrms.shorturl.utils.Base62EncodingFactory;
import com.prgrms.shorturl.domain.ShortUrl;
import com.prgrms.shorturl.dto.ShortUrlRequest;
import com.prgrms.shorturl.dto.ShortUrlResponse;
import com.prgrms.shorturl.exception.NoSuchOriginalUrlException;
import com.prgrms.shorturl.repository.ShortUrlRepository;
import com.prgrms.shorturl.utils.EncodingFactory;
import com.prgrms.shorturl.utils.HashAlgorithm;
import com.prgrms.shorturl.utils.HashIdFactory;
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

        HashIdFactory hashIdFactory = new HashIdFactory(HashAlgorithm.SHA_256);
        String hashId = checkDuplication(hashIdFactory.generate(originalUrl));

        EncodingFactory encodingFactory = new Base62EncodingFactory(hashId, originalUrl);
        return shortUrlRepository.save(encodingFactory.encode());
    }

    private String checkDuplication(String originalHash) {
        StringBuilder sb = new StringBuilder(originalHash.substring(0, 10));

        int index = 10;
        while(shortUrlRepository.findById(sb.toString()).isPresent()) {
            sb.append(originalHash.charAt(index++));
        }
        return sb.toString();
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
