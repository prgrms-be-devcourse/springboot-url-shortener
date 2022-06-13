package org.programmers.springbooturlshortener.repository;

import org.programmers.springbooturlshortener.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
    boolean existsByOriginal(String original);

    UrlCalledTimeProjection findCalledById(Long id);
}