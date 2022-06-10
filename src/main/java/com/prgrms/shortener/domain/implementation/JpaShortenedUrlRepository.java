package com.prgrms.shortener.domain.implementation;

import com.prgrms.shortener.domain.ShortenedUrl;
import com.prgrms.shortener.domain.ShortenedUrlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaShortenedUrlRepository extends JpaRepository<ShortenedUrl, Integer>, ShortenedUrlRepository {

}
