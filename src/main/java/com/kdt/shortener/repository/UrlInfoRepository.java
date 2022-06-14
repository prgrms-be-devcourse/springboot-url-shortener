package com.kdt.shortener.repository;

import com.kdt.shortener.domain.UrlInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlInfoRepository extends JpaRepository<UrlInfo, Long> {

    Optional<UrlInfo> findByOriginUrl(String originUrl);

}
