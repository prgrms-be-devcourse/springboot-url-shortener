package com.taehan.urlshortener.repository;

import com.taehan.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query("select u.url from Url u where u.shortUrl = :shortUrl")
    Optional<String> findByShortUrl(String shortUrl);

}
