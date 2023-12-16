package com.springboot.springbooturlshortner.service;

import com.springboot.springbooturlshortner.domain.Url;
import com.springboot.springbooturlshortner.exception.UrlException;
import com.springboot.springbooturlshortner.exception.UrlExceptionCode;
import com.springboot.springbooturlshortner.repository.UrlRepository;
import com.springboot.springbooturlshortner.util.Base62Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Util base62Util;
    @Value("${spring.start-url}")
    private String startUrl;

    @Transactional
    public String createShortenUrl(ShortenUrlRequestDto shortenUrlRequestDto) {
        Url url = urlRepository.save(shortenUrlRequestDto.toUrlEntity());
        String shortenUrl = startUrl + "/" + base62Util.encoding(url.getId());

        return shortenUrl;
    }

    @Transactional
    public String getOriginUrl(String shortenUrl) {
        Url url = urlRepository.findById(base62Util.decoding(shortenUrl)).orElseThrow(() -> new UrlException(UrlExceptionCode.NOT_FOUND));
        url.increaseRequestCntOne();

        return url.getOriginUrl();
    }
}
