package com.programmers.urlshortener.url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.urlshortener.url.domain.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findWithPessimisticLockByShortUrl(String shortUrl);

    Optional<Url> findByShortUrl(String shortUrl);
}
