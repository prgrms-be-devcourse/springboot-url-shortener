package com.sdardew.urlshortener.repository;

import com.sdardew.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {

  boolean existsUrlById(Long id);

  Optional<Url> findUrlByOriginalUrl(String originalUrl);

  Optional<Url> findUrlByShortUrl(String shortUrl);

}
