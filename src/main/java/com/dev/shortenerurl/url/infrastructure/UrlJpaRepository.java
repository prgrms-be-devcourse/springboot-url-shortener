package com.dev.shortenerurl.url.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.shortenerurl.url.domain.UrlRepository;
import com.dev.shortenerurl.url.domain.model.Url;

public interface UrlJpaRepository extends JpaRepository<Url, Long>, UrlRepository {

	Optional<Url> findByOriginUrl(String originUrl);
}
