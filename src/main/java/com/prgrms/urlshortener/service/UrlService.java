package com.prgrms.urlshortener.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.urlshortener.domain.ShortedUrl;
import com.prgrms.urlshortener.domain.Url;
import com.prgrms.urlshortener.dto.CreateShortenUrlRequest;
import com.prgrms.urlshortener.repository.UrlRepository;
import com.prgrms.urlshortener.service.encoder.UrlEncoder;
import com.prgrms.urlshortener.service.encoder.UrlEncoders;

@Service
@Transactional(readOnly = true)
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlEncoders urlEncoders;

    public UrlService(UrlRepository urlRepository, UrlEncoders urlEncoders) {
        this.urlRepository = urlRepository;
        this.urlEncoders = urlEncoders;
    }

    @Transactional
    public String createShortedUrl(CreateShortenUrlRequest request) {
        Url url = urlRepository.save(new Url(request.getOriginUrl()));
        UrlEncoder urlEncoder = urlEncoders.getUrlEncoderByType(request.getEncodedType());

        String shortUrl = urlEncoder.encode(url.getId());
        url.assignShortedUrl(new ShortedUrl(shortUrl));
        return shortUrl;
    }

    public String getOriginUrl(String shortedUrl) {
        Url url = urlRepository.findUrlByShortedUrl(new ShortedUrl(shortedUrl))
            .orElseThrow(() -> new IllegalArgumentException(shortedUrl + "로 저장된 URL이 없습니다."));

        url.addReqeustCount();
        return url.getOriginUrl();
    }

}
