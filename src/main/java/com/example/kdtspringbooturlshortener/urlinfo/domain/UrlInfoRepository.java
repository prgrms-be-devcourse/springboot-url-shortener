package com.example.kdtspringbooturlshortener.urlinfo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlInfoRepository extends JpaRepository<UrlInfo, Long> {

    Optional<UrlInfo> findByShortUrl(String shortUrl);
}
