package com.prgrms.urlshortener.domain.url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    @Query("select url from Url url where url.url = :url")
    Optional<Url> findByUrl(@Param("url") String url);
}
