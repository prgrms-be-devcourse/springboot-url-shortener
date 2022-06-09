package com.kdt.shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlInfoRepository extends JpaRepository<com.kdt.shortener.domain.UrlInfo, Long> {
}
