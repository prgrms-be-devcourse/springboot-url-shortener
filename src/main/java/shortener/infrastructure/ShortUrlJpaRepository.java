package shortener.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import shortener.domain.ShortUrl;

public interface ShortUrlJpaRepository extends JpaRepository<ShortUrl, Long> {
	Optional<ShortUrl> findShortUrlByEncodedUrl(String encodedUrl);
}
