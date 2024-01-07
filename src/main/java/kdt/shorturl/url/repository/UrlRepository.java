package kdt.shorturl.url.repository;

import kdt.shorturl.url.domain.Algorithm;
import kdt.shorturl.url.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByOriginUrlAndAlgorithm(String originUrl, Algorithm algorithm);

    Optional<Url> findByShortUrl(String shortUrl);
}
