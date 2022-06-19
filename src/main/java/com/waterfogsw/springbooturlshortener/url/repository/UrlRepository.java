package com.waterfogsw.springbooturlshortener.url.repository;

import com.waterfogsw.springbooturlshortener.url.entity.Url;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

  Optional<Url> findByHash(String hash);
}
