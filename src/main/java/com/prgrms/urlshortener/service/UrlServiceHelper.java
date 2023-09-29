package com.prgrms.urlshortener.service;

import com.prgrms.urlshortener.dao.UrlRepository;
import com.prgrms.urlshortener.domain.Urls;
import com.prgrms.urlshortener.utils.URLShorteningStrategy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Component
public class UrlServiceHelper {

    private final UrlRepository urlRepository;

    public UrlServiceHelper(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String generateUniqueShortenUrl(URLShorteningStrategy strategy, String originUrl, int maxRetries) {
        return Stream.generate(() -> strategy.shortenURL(originUrl))
                .limit(maxRetries)
                .filter(this::isUrlUnique)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("최대 시도 횟수 초과:" + maxRetries));
    }

    private boolean isUrlUnique(String shortenUrl) {
        return urlRepository.findByShortenUrl(shortenUrl).isEmpty();
    }

    public void saveShortenedUrl(String originUrl, String shortenUrl) {
        urlRepository.save(new Urls(originUrl, shortenUrl));
    }

    @Cacheable(value = "urls", key = "#shortenUrl", unless = "#result?.count < 10")
    public Urls findUrlByShortenUrl(String shortenUrl) {
        //System.out.println("db에서 조회");
        return urlRepository.findByShortenUrl(shortenUrl)
                .orElseThrow(() -> new NoSuchElementException("URL not found for: " + shortenUrl));
    }

    public String ensureUrlHasProtocol(String originalUrl) {
        if (isHasProtocol(originalUrl)) {
            return "https://" + originalUrl;
        }
        return originalUrl;
    }

    private boolean isHasProtocol(String originalUrl) {
        return (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://"));
    }
}