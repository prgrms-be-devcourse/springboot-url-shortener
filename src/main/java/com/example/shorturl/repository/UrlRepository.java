package com.example.shorturl.repository;

import com.example.shorturl.domain.Algorithm;
import com.example.shorturl.domain.Url;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

    boolean existsUrlByOriginUrlAndAlgorithm(String originUrl, Algorithm algorithm);

    Optional<Url> findUrlByOriginUrlAndAlgorithm(String originUrl, Algorithm algorithm);

    Optional<Url> findUrlByShortUrl(String shortUrl);
}
