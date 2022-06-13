package com.urlshortener.shorturl.service;

import com.urlshortener.shorturl.component.ShortUrlGenerator;
import com.urlshortener.shorturl.model.converter.UrlConverter;
import com.urlshortener.shorturl.model.dto.CreateRequest;
import com.urlshortener.shorturl.model.dto.CreateResponse;
import com.urlshortener.shorturl.model.entity.Url;
import com.urlshortener.shorturl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class SimpleUrlService implements UrlService {
    private final UrlRepository urlRepository;
    private final UrlConverter urlConverter;
    private final ShortUrlGenerator shortUrlGenerator;

    @Override
    public CreateResponse save(CreateRequest request) {
        Url result = urlRepository.findUrlByOriginUrlEquals(request.getUrl());
        if (Objects.nonNull(result)) {
            return urlConverter.getCreateResponseFrom(result);
        }
        String shortUrl = getUniqueShortUrl();
        Url url = new Url(request.getUrl(), shortUrl);
        urlRepository.save(url);
        return urlConverter.getCreateResponseFrom(url);
    }

    @Override
    public String findOne(String shortUrl) {
        Url originUrl = urlRepository.findUrlByShortUrlEquals(shortUrl);
        urlRepository.updateCount(originUrl.getId());
        return originUrl.getOriginUrl();
    }

    private String getUniqueShortUrl() {
        while (true) {
            String shortUrl = shortUrlGenerator.generate(null);
            Url result = urlRepository.findUrlByShortUrlEquals(shortUrl);
            if (Objects.isNull(result)) {
                return shortUrl;
            }
        }
    }
}
