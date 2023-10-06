package com.tangerine.urlshortener.url.repository;


import com.tangerine.urlshortener.url.model.UrlMapping;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingJpaRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByOriginUrl(OriginUrl originUrl);
}
