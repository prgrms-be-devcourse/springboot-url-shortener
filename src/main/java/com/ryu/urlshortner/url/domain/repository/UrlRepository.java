package com.ryu.urlshortner.url.domain.repository;

import com.ryu.urlshortner.url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByOriginUrl(String originUrl);
    Optional<Url> findBySeq(long seq);
}
