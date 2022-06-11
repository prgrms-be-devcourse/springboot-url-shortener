package shortUrl.shortUrl.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shortUrl.shortUrl.domain.entity.Url;
import shortUrl.shortUrl.domain.value.Algorithm;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortUrl(String shortUrl);

    boolean existsByShortUrl(String shortUrl);

    @Query("select u from Url u where u.originalUrl = :originalUrl and u.algorithm = :algorithm")
    Optional<Url> findByOriginalUrlAndAlgorithm(@Param("originalUrl") String originalUrl,
                                          @Param("algorithm") Algorithm algorithm);

}
