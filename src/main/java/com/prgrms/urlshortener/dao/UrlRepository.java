package com.prgrms.urlshortener.dao;

import com.prgrms.urlshortener.domain.Urls;

import java.util.Optional;

public interface UrlRepository {

    Optional<Urls> findByShortenUrl(String shortenUrl);

    Urls save(Urls urls);
}