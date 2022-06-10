package com.urlshortener.shorturl.repository;

import com.urlshortener.shorturl.model.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findUrlByShortUrlEquals(String url);

    Url findUrlByLongUrlEquals(String url);

    @Modifying
    @Query("update Url u set u.count = u.count + 1 where u.id = :id")
    int updateCount(Long id);
}
