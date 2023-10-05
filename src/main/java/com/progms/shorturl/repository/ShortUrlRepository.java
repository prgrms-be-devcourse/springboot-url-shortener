package com.progms.shorturl.repository;

import com.progms.shorturl.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
}
