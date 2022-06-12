package com.prgrms.shorturl.repository;

import com.prgrms.shorturl.domain.Url;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByOriginalUrl(String originalUrl);
}
