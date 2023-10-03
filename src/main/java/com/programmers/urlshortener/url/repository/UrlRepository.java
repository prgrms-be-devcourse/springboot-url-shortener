package com.programmers.urlshortener.url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.programmers.urlshortener.url.domain.Url;

import jakarta.persistence.LockModeType;

public interface UrlRepository extends JpaRepository<Url, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Url> findWithPessimisticLockByShortUrl(String shortUrl);

    Optional<Url> findByShortUrl(String shortUrl);
}
