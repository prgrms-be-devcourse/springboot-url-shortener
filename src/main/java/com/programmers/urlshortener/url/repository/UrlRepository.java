package com.programmers.urlshortener.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.urlshortener.url.entity.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
