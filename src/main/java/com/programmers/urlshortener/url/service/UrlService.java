package com.programmers.urlshortener.url.service;

import static com.programmers.urlshortener.common.exception.ExceptionRule.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.urlshortener.common.exception.UrlException;
import com.programmers.urlshortener.url.domain.Url;
import com.programmers.urlshortener.url.dto.request.ShortUrlCreateRequest;
import com.programmers.urlshortener.url.dto.response.UrlResponse;
import com.programmers.urlshortener.url.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public UrlResponse createShortUrl(ShortUrlCreateRequest shortUrlCreateRequest) {
        Url savedUrl = urlRepository.save(shortUrlCreateRequest.toEntity());
        savedUrl.convertToShortUrl();

        return UrlResponse.from(savedUrl);
    }

    @Transactional
    public String findOriginalUrlByShortUrl(String shortUrl) {
        Url url = urlRepository.findWithPessimisticLockByShortUrl(shortUrl)
            .orElseThrow(() -> new UrlException(SHORTENURL_NOT_EXIST));

        url.increaseCount();

        return url.getOriginalUrl();
    }

    public UrlResponse findUrlByShortUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
            .orElseThrow(() -> new UrlException(SHORTENURL_NOT_EXIST));

        return UrlResponse.from(url);
    }
}
