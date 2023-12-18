package com.prgrms.url_shortener.service;

import com.prgrms.url_shortener.algorithm.Base62Algorithm;
import com.prgrms.url_shortener.dto.ShortenUrlRequest;
import com.prgrms.url_shortener.dto.ShortenUrlResponse;
import com.prgrms.url_shortener.entity.Url;
import com.prgrms.url_shortener.exception.CustomException;
import com.prgrms.url_shortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    @Value("${spring.base-url}")
    private String BASE_URL;

    @Transactional
    public ShortenUrlResponse getShortUrl(ShortenUrlRequest request) {
        Url savedUrl = getSavedUrl(request.originUrl());

        savedUrl.increaseRequestCount();
        String shortenUrl = BASE_URL + "/" + Base62Algorithm.encode(savedUrl.getId());

        return new ShortenUrlResponse(shortenUrl, savedUrl.getRequestCount());
    }

    @Transactional(readOnly = true)
    public String getOriginUrl(String shortUri) {
        Long urlId = Base62Algorithm.decode(shortUri);
        Url url = urlRepository.findById(urlId).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 URL입니다.");
        });

        return url.getOriginUrl();
    }

    private Url getSavedUrl(String originUrl) {
        Url savedUrl;
        if (urlRepository.existsByOriginUrl(originUrl)) {
            savedUrl = urlRepository.findByOriginUrl(originUrl).orElseThrow();
        } else {
            savedUrl = urlRepository.save(new Url(originUrl));
        }
        return savedUrl;
    }
}
