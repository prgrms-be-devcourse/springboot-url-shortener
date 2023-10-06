package com.prgrms.urlshortener.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.urlshortener.domain.entity.UrlInfo;

public interface UrlRepository extends JpaRepository<UrlInfo, Long> {
}
