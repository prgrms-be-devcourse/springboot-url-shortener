package com.prgrms.shorturl.url.repository;

import com.prgrms.shorturl.url.exception.ExistedOriginUrlException;
import com.prgrms.shorturl.url.model.Urls;

import jakarta.persistence.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.prgrms.shorturl.global.error.ErrorCode.DUPLICATED_CREATE_ORIGIN_URL_ERROR;

@Slf4j
@Repository
@CacheConfig(cacheNames = "urls")
public class ShortUrlRepositoryImpl implements ShortUrlRepository {

    private final ShortUrlJpaRepository shortUrlJpaRepository;

    public ShortUrlRepositoryImpl(ShortUrlJpaRepository shortUrlJpaRepository) {
        this.shortUrlJpaRepository = shortUrlJpaRepository;
    }

    @Override
    public void isExistedOriginUrl(String originUrl) {
        if (findByOriginUrl(originUrl).isPresent()) {
            throw new ExistedOriginUrlException(DUPLICATED_CREATE_ORIGIN_URL_ERROR);
        }
    }

    @Override
    @Cacheable(value = "urls", key = "#shortUrl", unless = "#result?.getRequestCount() < 10")
    public Urls getUrlByShortUrl(String shortUrl) {
        return findByShortUrl(shortUrl).orElseThrow(() -> new EntityNotFoundException("존재하지 않은 URL입니다."));
    }

    @Override
    public Optional<Urls> findByOriginUrl(String originUrl) {
        return shortUrlJpaRepository.findByOriginUrl(originUrl);
    }

    @Override
    public Optional<Urls> findByShortUrl(String shortUrl) {
        Optional<Urls> byShortUrl = shortUrlJpaRepository.findByShortUrl(shortUrl);

        return byShortUrl;
    }

    @Override
    public Urls save(Urls urls) {
        return shortUrlJpaRepository.save(urls);
    }

}
