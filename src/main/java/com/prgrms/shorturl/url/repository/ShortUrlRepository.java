package com.prgrms.shorturl.url.repository;

import com.prgrms.shorturl.url.model.Urls;

import java.util.Optional;

public interface ShortUrlRepository {

    void isExistedOriginUrl(String originUrl);

    Urls getUrlByShortUrl(String shortUrl);

    Optional<Urls> findByOriginUrl(String originUrl);

    Optional<Urls> findByShortUrl(String shortUrl);

    Urls save(Urls urls);

}
