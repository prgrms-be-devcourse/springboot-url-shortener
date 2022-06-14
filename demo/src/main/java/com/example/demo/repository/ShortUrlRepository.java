package com.example.demo.repository;

import com.example.demo.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<Url, Integer> {

    @Query("SELECT u FROM Url u WHERE u.originUrl =?1")
    Optional<Url> findUrlByOriginUrl(String url);
}
