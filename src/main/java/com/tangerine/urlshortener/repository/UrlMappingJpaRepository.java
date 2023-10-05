package com.tangerine.urlshortener.repository;

import com.tangerine.urlshortener.model.UrlMapping;
import com.tangerine.urlshortener.model.vo.OriginUrl;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingJpaRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByOriginUrl(OriginUrl originUrl);
}
