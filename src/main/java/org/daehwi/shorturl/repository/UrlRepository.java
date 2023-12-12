package org.daehwi.shorturl.repository;

import org.daehwi.shorturl.domain.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<ShortUrl, Long> {
}
