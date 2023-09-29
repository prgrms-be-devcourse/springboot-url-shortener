package com.prgrms.urlshortener.service;

import com.prgrms.urlshortener.domain.Urls;
import com.prgrms.urlshortener.utils.URLShorteningStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlServiceHelper urlServiceHelper;
    private final Map<String, URLShorteningStrategy> strategies;

    public UrlServiceImpl(UrlServiceHelper urlServiceHelper, Map<String, URLShorteningStrategy> strategies) {
        this.urlServiceHelper = urlServiceHelper;
        this.strategies = strategies;

    }

    @Override
    public String createShortenUrl(String originUrl, String strategyType) {
        URLShorteningStrategy strategy = Optional.ofNullable(strategies.get(strategyType))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 단축 전략"));

        final int MAX_RETRIES = 5;
        String shortenUrl = urlServiceHelper.generateUniqueShortenUrl(strategy, originUrl, MAX_RETRIES);
        urlServiceHelper.saveShortenedUrl(originUrl, shortenUrl);
        return shortenUrl;
    }

    @Override
    public String getOriginalUrl(String shortenUrl) {
         Urls url = urlServiceHelper.findUrlByShortenUrl(shortenUrl);
         return urlServiceHelper.ensureUrlHasProtocol(url.getOriginUrl());
    }
}