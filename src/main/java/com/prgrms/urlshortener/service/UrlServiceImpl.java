package com.prgrms.urlshortener.service;

import com.prgrms.urlshortener.dao.UrlRepository;
import com.prgrms.urlshortener.domain.Urls;
import com.prgrms.urlshortener.utils.URLShorteningStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final Map<String, URLShorteningStrategy> strategies;

    public UrlServiceImpl(UrlRepository urlRepository, Map<String, URLShorteningStrategy> strategies) {
        this.urlRepository = urlRepository;
        this.strategies = strategies;

    }

    @Override
    public String createShortenUrl(String originUrl, String strategyType) {
        URLShorteningStrategy strategy = Optional.ofNullable(strategies.get(strategyType))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 단축 전략"));

        final int MAX_RETRIES = 5;

        return Stream.generate(() -> strategy.shortenURL(originUrl))
                .limit(MAX_RETRIES)
                .filter(shortenUrl -> urlRepository.findByShortenUrl(shortenUrl).isEmpty())
                .findFirst()
                .map(shortenUrl -> {
                    urlRepository.save(new Urls(originUrl, shortenUrl));
                    return shortenUrl;
                })
                .orElseThrow(() -> new RuntimeException("최대 시도 횟수 초과:" + MAX_RETRIES));
    }


}