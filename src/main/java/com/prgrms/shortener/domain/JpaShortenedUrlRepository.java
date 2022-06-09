package com.prgrms.shortener.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaShortenedUrlRepository extends JpaRepository<ShortenedUrl, Long>, ShortenedUrlRepository {

}
