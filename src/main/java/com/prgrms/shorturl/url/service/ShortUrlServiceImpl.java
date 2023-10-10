package com.prgrms.shorturl.url.service;

import com.prgrms.shorturl.url.encoder.ShortUrlStrategy;
import com.prgrms.shorturl.url.encoder.ShortUrlStrategyConfig;
import com.prgrms.shorturl.url.model.ShortUrl;
import com.prgrms.shorturl.url.model.Urls;
import com.prgrms.shorturl.url.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlStrategyConfig shortUrlStrategyConfig;
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlServiceImpl(ShortUrlStrategyConfig shortUrlStrategyConfig, ShortUrlRepository shortUrlRepository) {
        this.shortUrlStrategyConfig = shortUrlStrategyConfig;
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    @Transactional
    public String creatShortUrl(String originUrl, String strategyType) {
        shortUrlRepository.isExistedOriginUrl(originUrl);
        Urls urls = shortUrlRepository.save(new Urls(originUrl));

        ShortUrlStrategy shortUrlStrategy = shortUrlStrategyConfig.find(strategyType);
        String shortUrl = shortUrlStrategy.encodeOriginUrl(urls.getId());
        urls.registerShortUrl(new ShortUrl(shortUrl));

        return shortUrl;
    }

    @Override
    @Transactional
    public String getOriginUrl(String shortUrl) {
        Urls url = shortUrlRepository.getUrlByShortUrl(shortUrl);

        return url.getOriginUrl();
    }

}
