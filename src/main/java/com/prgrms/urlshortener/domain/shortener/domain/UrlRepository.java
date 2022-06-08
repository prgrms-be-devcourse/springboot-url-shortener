package com.prgrms.urlshortener.domain.shortener.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
