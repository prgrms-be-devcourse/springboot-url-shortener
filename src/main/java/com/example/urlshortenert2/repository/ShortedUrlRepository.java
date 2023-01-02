package com.example.urlshortenert2.repository;

import com.example.urlshortenert2.model.ShortedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortedUrlRepository extends JpaRepository<ShortedUrl, Long> {
}
