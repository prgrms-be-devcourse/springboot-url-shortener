package com.springboot.springbooturlshortner.repository;

import com.springboot.springbooturlshortner.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
}