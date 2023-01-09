package com.programmers.springbooturlshortener.domain.url;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByOriginUrl(String originUrl);

    Optional<Url> findByShortUrl(String shortUrl);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from Url u where u.originUrl = :originUrl")
    Optional<Url> findByOriginUrlWithLock(String originUrl);
}