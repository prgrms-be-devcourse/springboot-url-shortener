package com.prgrms.short_url.repository;

import com.prgrms.short_url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Integer> {

    Optional<Url> getUrlByOriginalUrl(String originalUrl);

    Optional<Url> getUrlByShortUrl(String shortUrl);
}
