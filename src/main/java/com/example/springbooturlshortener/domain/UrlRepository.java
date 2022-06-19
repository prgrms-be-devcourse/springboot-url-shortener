package com.example.springbooturlshortener.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

  Optional<Url> findById(Long id);

  Optional<Url> findByOriginalUrl(String originalUrl);
}
