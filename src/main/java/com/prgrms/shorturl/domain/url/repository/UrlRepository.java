package com.prgrms.shorturl.domain.url.repository;

import com.prgrms.shorturl.domain.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByOriginUrl(String originUrl);

    Optional<Url> findByShortUrl(String shortUrl);
}
