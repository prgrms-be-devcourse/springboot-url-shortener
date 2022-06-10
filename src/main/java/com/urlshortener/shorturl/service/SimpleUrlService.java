package com.urlshortener.shorturl.service;

import com.urlshortener.shorturl.component.ShortUrlGenerator;
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
    private final ShortUrlGenerator shortUrlGenerator;

    @Override
    public CreateResponse save(CreateRequest request) {
        CreateResponse response = new CreateResponse();
        Url test = urlRepository.findUrlByLongUrlEquals(request.getUrl());
        if (Objects.nonNull(test)) {
            response.setShortUrl(test.getShortUrl());
            return response;
        } else {
            String shortUrl = shortUrlGenerator.generate(request.getUrl());
            Url url = new Url();
            url.setLongUrl(request.getUrl());
            url.setShortUrl(shortUrl);
            url.setCount(0);
            urlRepository.save(url);
            response.setShortUrl(shortUrl);
        }
        return response;
    }

    @Override
    public String findOne(String shortUrl) {
        Url originUrl = urlRepository.findUrlByShortUrlEquals(shortUrl);
        urlRepository.updateCount(originUrl.getId());
        return originUrl.getLongUrl();
    }
}
