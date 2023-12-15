package org.prgms.springbooturlshortener.domain.shorturl.service;

import org.prgms.springbooturlshortener.domain.shorturl.ShortUrl;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlErrorCode;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlException;
import org.prgms.springbooturlshortener.domain.shorturl.repository.ShortUrlRepository;
import org.prgms.springbooturlshortener.domain.shorturl.service.transformer.UrlTransformer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        Optional<ShortUrl> foundShortUrl = urlRepository.findByOriginalUrl(originalUrl);

        if (foundShortUrl.isPresent()) {
            return foundShortUrl.get().getTransformedUrl();
        }

        String generatedUrl;

        generatedUrl = getRandomUrl();

        ShortUrl savedShortUrl = getSavedShortUrl(originalUrl, generatedUrl);

        return savedShortUrl.getTransformedUrl();
    }

    public String getOriginalUrl(String encodedUrl) {
        ShortUrl shortUrl = urlRepository.findById(encodedUrl)
                .orElseThrow(() -> new UrlException(UrlErrorCode.ORIGINAL_URL_NOT_FOUND));

        increaseVisit(shortUrl);

        return shortUrl.getOriginalUrl();
    }

    private ShortUrl getSavedShortUrl(String originalUrl, String generatedUrl) {
        ShortUrl shortUrl = ShortUrl.builder()
                .transformedUrl(generatedUrl)
                .originalUrl(originalUrl)
                .build();
        return urlRepository.save(shortUrl);
    }

    private String getRandomUrl() {
        Random random = new Random();
        String generatedUrl;
        int randomInt;

        do  {
            randomInt = random.nextInt(0, Integer.MAX_VALUE);
            generatedUrl = urlTransformer.generateUrl(randomInt);
        } while (urlRepository.findById(generatedUrl).isPresent());

        return generatedUrl;
    }

    private void increaseVisit(ShortUrl shortUrl) {
        shortUrl.increaseVisit();
        urlRepository.save(shortUrl);
    }
}
