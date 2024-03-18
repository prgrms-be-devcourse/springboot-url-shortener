package com.prgrms.springbooturlshortener.service;

import com.prgrms.springbooturlshortener.converter.Base62ShortenConverter;
import com.prgrms.springbooturlshortener.domain.Url;
import com.prgrms.springbooturlshortener.domain.UrlRepository;
import com.prgrms.springbooturlshortener.dto.ShorteningResponseUrl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository repository;
    private final Base62ShortenConverter base62ShortenConverter;

    @Transactional
    public ShorteningResponseUrl generateShortUrl(String request) {
        Url url = new Url(request);
        Url savedUrl = repository.save(url); //OriginalUrl 저장
        String shortUrl = base62ShortenConverter.encode(savedUrl.getId()); //shortUrl 생성
        url.setShortUrl(shortUrl);

        return new ShorteningResponseUrl(savedUrl.getOriginalUrl(), shortUrl);
    }

    @Transactional(readOnly = true)
    public String getOriginalUrl(String shortUrl) {
        Url url = repository.findOriginalUrlByShortUrl(shortUrl)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 shortUrl입니다."));

        return url.getOriginalUrl();
    }
}

