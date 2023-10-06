package com.prgrms.wonu606.shorturl.service;

import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateParam;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateResult;
import org.springframework.stereotype.Service;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService {

    @Override
    public ShortenUrlCreateResult createShortenUrl(ShortenUrlCreateParam param) {
        return new ShortenUrlCreateResult("http://localhost:8080/3rLcvBZ");
    }
}
