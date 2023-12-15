package org.prgms.springbooturlshortener.domain.shorturl.repository;

import org.prgms.springbooturlshortener.domain.shorturl.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {
}
