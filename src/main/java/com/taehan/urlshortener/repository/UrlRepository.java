package com.taehan.urlshortener.repository;

import com.taehan.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query(value = "select max(id) from Url")
    int getMaxId();
}
