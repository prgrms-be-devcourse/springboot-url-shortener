package com.jhs.shortenerurl.repository;

import com.jhs.shortenerurl.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long>  {

    @Query("select u.shortenUrl from Url u where u.originUrl =:originUrl")
    Optional<String> findByOriginUrl(@Param("originUrl") String originUrl);

}
