package org.prgms.springbooturlshortener.domain.shorturl.repository;

import org.prgms.springbooturlshortener.domain.shorturl.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {
    @Query(value = "UPDATE ShortUrl s SET s.visitCount = s.visitCount + 1 WHERE s.transformedUrl = :transformedUrl")
    void updateVisitCount(String transformedUrl);
}
