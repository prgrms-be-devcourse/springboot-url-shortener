package com.example.springbootboardjpa.repository;

import com.example.springbootboardjpa.domain.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortId(String shortId);
    Optional<ShortUrl> findByUrl(String url);
}
