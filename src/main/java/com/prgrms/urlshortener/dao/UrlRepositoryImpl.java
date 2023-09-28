package com.prgrms.urlshortener.dao;

import com.prgrms.urlshortener.domain.Urls;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class UrlRepositoryImpl implements UrlRepository {

    private final Map<String, Urls> urlStore;

    public UrlRepositoryImpl(Map<String, Urls> urlStore) {
        this.urlStore = urlStore;
    }

    @Override
    public Optional<Urls> findByShortenUrl(String shortenUrl) {
        return Optional.ofNullable(urlStore.get(shortenUrl));
    }

    @Override
    public Urls save(Urls urls) {
        urlStore.put(urls.getShortenUrl(), urls);

        return urls;
    }
}