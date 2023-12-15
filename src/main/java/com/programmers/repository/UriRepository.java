package com.programmers.repository;

import com.programmers.entity.UriEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UriRepository extends JpaRepository<UriEntity, Long> {
    Optional<UriEntity> findByShortUriEquals(String shortUri);
    Optional<UriEntity> findByOriginalUriEquals(String originalUri);
}
