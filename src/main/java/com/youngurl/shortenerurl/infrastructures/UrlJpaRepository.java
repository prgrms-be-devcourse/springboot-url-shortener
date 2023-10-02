package com.youngurl.shortenerurl.infrastructures;

import com.youngurl.shortenerurl.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByEncodedUrl(String encodedUrl);
}
