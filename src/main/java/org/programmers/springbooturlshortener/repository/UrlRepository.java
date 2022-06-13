package org.programmers.springbooturlshortener.repository;

import org.programmers.springbooturlshortener.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    boolean existsByOriginal(String original);

    Optional<OriginalUrlProjection> findOriginalById(Long id);
}