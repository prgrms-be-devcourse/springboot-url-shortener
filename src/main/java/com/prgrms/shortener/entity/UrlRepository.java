package com.prgrms.shortener.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

  Optional<Url> findByOriginalUrl(String originalUrl);

  Optional<Url> findByUniqueKey(String uniqueKey);
}
