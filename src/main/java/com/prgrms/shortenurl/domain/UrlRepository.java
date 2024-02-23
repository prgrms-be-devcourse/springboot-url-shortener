package com.prgrms.shortenurl.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Id> {
    Optional<Url> findByShortenKey(String key);
    Optional<Url> findByOriginUrl(String originUrl);
}
