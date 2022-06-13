package com.urlshortener.url.repository;

import com.urlshortener.url.model.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findUrlByShortUrlEquals(String url);

    Url findUrlByOriginUrlEquals(String url);

    @Modifying
    @Query("update Url u set u.count = u.count + 1 where u.id = :id")
    int updateCount(Long id);
}
