package com.su.urlshortener.url.repository;

import com.su.urlshortener.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShotToken(String shotToken);
}
