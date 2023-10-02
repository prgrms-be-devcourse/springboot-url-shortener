package com.youngurl.shortenerurl.infrastructures;

import com.youngurl.shortenerurl.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {
}
