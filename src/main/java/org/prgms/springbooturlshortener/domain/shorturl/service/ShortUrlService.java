package org.prgms.springbooturlshortener.domain.shorturl.service;

import java.util.Optional;
import org.prgms.springbooturlshortener.domain.shorturl.ShortUrl;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlErrorCode;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlException;
import org.prgms.springbooturlshortener.domain.shorturl.repository.ShortUrlRepository;
import org.prgms.springbooturlshortener.domain.shorturl.service.dto.TransformedShortUrlDto;
import org.prgms.springbooturlshortener.domain.shorturl.service.transformer.UrlTransformer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlService {
    private final UrlTransformer urlTransformer;
    private final ShortUrlRepository urlRepository;

    public ShortUrlService(@Qualifier("BASE62") UrlTransformer urlTransformer, ShortUrlRepository urlRepository) {
        this.urlTransformer = urlTransformer;
        this.urlRepository = urlRepository;
    }

    public TransformedShortUrlDto saveOriginalUrl(String originalUrl) {
        Optional<ShortUrl> foundShortUrl = urlRepository.findByOriginalUrl(originalUrl);

        if (foundShortUrl.isPresent()) {
            ShortUrl shortUrl = foundShortUrl.get();
            return shortUrl.toTransformedShortUrlDto();
        }

        String generatedUrl = getRandomUrl();

        ShortUrl savedShortUrl = getSavedShortUrl(originalUrl, generatedUrl);

        return savedShortUrl.toTransformedShortUrlDto();
    }

    public String getOriginalUrl(String transformedUrl) {
        ShortUrl shortUrl = urlRepository.findById(transformedUrl)
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
        String generatedUrl;

        do  {
            generatedUrl = urlTransformer.transform();
        } while (urlRepository.findById(generatedUrl).isPresent());

        return generatedUrl;
    }

    private void increaseVisit(ShortUrl shortUrl) {
        shortUrl.increaseVisit();
        urlRepository.save(shortUrl);
    }
}
