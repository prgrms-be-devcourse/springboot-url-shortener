package org.daehwi.shorturl.repository;

import jakarta.persistence.LockModeType;
import org.daehwi.shorturl.domain.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<ShortUrl, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ShortUrl> findByShortUrl(String shortUrl);
}
