package com.prgrmrs.urlshortener.repository;

import com.prgrmrs.urlshortener.model.UrlMapping;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByHash(String hash);
}
