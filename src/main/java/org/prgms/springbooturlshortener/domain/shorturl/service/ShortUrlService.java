package org.prgms.springbooturlshortener.domain.shorturl.service;

import org.prgms.springbooturlshortener.domain.shorturl.ShortUrl;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlErrorCode;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlException;
import org.prgms.springbooturlshortener.domain.shorturl.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShortUrlService {
    private final UrlTransformer urlTransformer;
    private final ShortUrlRepository urlRepository;

    public ShortUrlService(@Qualifier("BASE62") UrlTransformer urlTransformer, ShortUrlRepository urlRepository) {
        this.urlTransformer = urlTransformer;
        this.urlRepository = urlRepository;
    }

    public String saveOriginalUrl(String originalUrl) {
        Random random = new Random();
        int randomInt = random.nextInt();
        String generatedUrl = urlTransformer.generateUrl(randomInt);

        while (urlRepository.findById(generatedUrl).isPresent()) {
            randomInt = random.nextInt();
            generatedUrl = urlTransformer.generateUrl(randomInt);
        }

        ShortUrl shortUrl = ShortUrl.builder()
                .transformedUrl(generatedUrl)
                .originalUrl(originalUrl)
                .build();
        ShortUrl savedShortUrl = urlRepository.save(shortUrl);

        return savedShortUrl.getTransformedUrl();
    }

    public String getOriginalUrl(String encodedUrl) {
        ShortUrl shortUrl = urlRepository.findById(encodedUrl)
                .orElseThrow(() -> new UrlException(UrlErrorCode.ORIGINAL_URL_NOT_FOUND));

        increaseVisit(shortUrl);

        return shortUrl.getTransformedUrl();
    }

    private void increaseVisit(ShortUrl shortUrl) {
        shortUrl.increaseVisit();
        urlRepository.updateVisitCount(shortUrl.getTransformedUrl());
    }
}
