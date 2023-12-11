package com.pgms.shorturlcoredomain.url;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
     Optional<Url> findUrlByOriginal(String original);
     Optional<Url> findUrlByShortUrl(String shortUrl);
}
