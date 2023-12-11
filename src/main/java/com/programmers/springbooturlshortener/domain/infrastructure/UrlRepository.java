package com.programmers.springbooturlshortener.domain.infrastructure;

import com.programmers.springbooturlshortener.domain.entity.EncodeType;
import com.programmers.springbooturlshortener.domain.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {

    Optional<Url> findByLongUrlAndEncodeType(String longUrl, EncodeType encodeType);
}