package com.programmers.springbooturlshortener.domain.infrastructure;

import com.programmers.springbooturlshortener.domain.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
}