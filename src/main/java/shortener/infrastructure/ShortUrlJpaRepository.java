package shortener.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shortener.domain.ShortUrl;

public interface ShortUrlJpaRepository extends JpaRepository<ShortUrl, Long> {

	Optional<ShortUrl> findShortUrlByEncodedUrl(String encodedUrl);

	@Modifying
	@Query("UPDATE ShortUrl su SET su.clicks = :clicks WHERE su.id = :id")
	void updateClicks(@Param("id") Long id, @Param("clicks") Long clicks);
}
