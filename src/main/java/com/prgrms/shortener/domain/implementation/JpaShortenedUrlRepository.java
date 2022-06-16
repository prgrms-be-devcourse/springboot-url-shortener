package com.prgrms.shortener.domain.implementation;

import com.prgrms.shortener.domain.ShortenedUrl;
import com.prgrms.shortener.domain.ShortenedUrlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaShortenedUrlRepository extends JpaRepository<ShortenedUrl, Integer>, ShortenedUrlRepository {

  @Override
  @Query("update ShortenedUrl u set u.count = u.count + 1 where u.id = :id")
  @Modifying(flushAutomatically = true, clearAutomatically = true)
  void increaseCount(@Param("id") Integer id);
}
