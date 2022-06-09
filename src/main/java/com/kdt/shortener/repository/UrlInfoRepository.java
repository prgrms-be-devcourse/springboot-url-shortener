package com.kdt.shortener.repository;

import com.kdt.shortener.domain.UrlInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlInfoRepository extends JpaRepository<UrlInfo, Long> {

    Optional<UrlInfo> findByOriginUrl(String originUrl);

}
