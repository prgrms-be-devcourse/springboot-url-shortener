package com.example.urlshortenert2.repository;

import com.example.urlshortenert2.model.ShortedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortedUrlRepository extends JpaRepository<ShortedUrl, Long> {
    Optional<ShortedUrl> findShortedUrlByShorteningKey(String shorteningKey);
    Optional<ShortedUrl> findShortedUrlByOriginUrl(String originUrl);
}
