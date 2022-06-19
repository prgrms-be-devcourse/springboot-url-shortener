package com.waterfogsw.springbooturlshortener.url.repository;

import com.waterfogsw.springbooturlshortener.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

}
