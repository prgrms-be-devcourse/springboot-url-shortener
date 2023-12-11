package com.prgrms.shorturl.repository;

import com.prgrms.shorturl.domain.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
}
