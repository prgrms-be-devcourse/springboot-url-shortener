package com.prgrms.shortenurl.service;

import com.google.common.hash.Hashing;
import com.prgrms.shortenurl.domain.Url;
import com.prgrms.shortenurl.domain.UrlRepository;
import com.prgrms.shortenurl.exception.UrlNotFoundException;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

@Service
public class UrlService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UrlRepository urlRepository;
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private String shortenUrlByMurMur(@NonNull String originUrl) {
        return Hashing.murmur3_32_fixed().hashString(originUrl, Charset.defaultCharset()).toString();
    }

    @Transactional
    public Url addLink(@NonNull String originUrl) {
        String key = shortenUrlByMurMur(originUrl);
        Url url = urlRepository.findByShortenKey(key)
                .orElse(
                        Url.builder()
                                .shortenKey(key)
                                .originUrl(originUrl)
                                .shortenUrl("http://localhost:8090/" + key).build()
                );
        return urlRepository.save(url);
    }

    @Transactional(readOnly = true)
    public String getUrlByKey(@NonNull String key) {
        Url url = urlRepository.findByShortenKey(key)
                .orElseThrow(UrlNotFoundException::new);
        return url.getOriginUrl();
    }
}
