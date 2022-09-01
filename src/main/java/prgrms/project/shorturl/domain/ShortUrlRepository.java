package prgrms.project.shorturl.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import prgrms.project.shorturl.domain.ShortUrl;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByShortenUrl(String shortenUrl);
}
