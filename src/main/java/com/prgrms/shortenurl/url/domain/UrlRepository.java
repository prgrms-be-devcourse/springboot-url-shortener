package com.prgrms.shortenurl.url.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, String> {
    Optional<Url> findByShortenKey(String key);
}
