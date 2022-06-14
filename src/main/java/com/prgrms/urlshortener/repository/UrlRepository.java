package com.prgrms.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.urlshortener.domain.ShortedUrl;
import com.prgrms.urlshortener.domain.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findUrlByShortedUrl(ShortedUrl shortedUrl);

}
