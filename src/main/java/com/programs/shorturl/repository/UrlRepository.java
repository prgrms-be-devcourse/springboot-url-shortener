package com.programs.shorturl.repository;

import com.programs.shorturl.domain.Url;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

  Optional<Url> findByShortUrl(String shortUrl);

  Optional<Url> findByUrl(String Url);
}
