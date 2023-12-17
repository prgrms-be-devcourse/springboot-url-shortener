package com.prgrms.url_shortener.repository;

import com.prgrms.url_shortener.entity.Url;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByOriginUrl(String originUrl);

    boolean existsByOriginUrl(String originUrl);
}
