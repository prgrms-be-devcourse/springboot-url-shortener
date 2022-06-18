package com.example.springbooturlshortener.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Long, Url> {
  Long save(Url url);
}
