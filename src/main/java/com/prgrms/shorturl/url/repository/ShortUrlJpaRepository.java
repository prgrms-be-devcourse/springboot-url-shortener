package com.prgrms.shorturl.url.repository;

import com.prgrms.shorturl.url.model.Urls;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShortUrlJpaRepository extends JpaRepository<Urls, Long> {

    Optional<Urls> findByOriginUrl(@Param(value = "originUrl") String originUrl);

    @Query("select u from Urls u where u.shortUrl.shortUrl=:shortUrl")
    Optional<Urls> findByShortUrl(@Param(value = "shortUrl") String shortUrl);

}
