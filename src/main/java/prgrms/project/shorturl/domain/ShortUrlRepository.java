package prgrms.project.shorturl.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByShortenUrl(String shortenUrl);
}
