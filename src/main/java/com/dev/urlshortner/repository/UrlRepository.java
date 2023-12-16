package com.dev.urlshortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.urlshortner.domain.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
	Optional<Url> findByShortKey(String shortKey);

	boolean existsByShortKey(String shortKey);
}
