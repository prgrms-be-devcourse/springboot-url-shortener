package org.prgrms.urlshortener.respository;

import java.util.Optional;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {


	Optional<Url> findByOriginUrl(String originUrl);

	boolean existsByAlgorithmAndOriginUrl(Algorithm algorithm, String originUrl);

	Optional<Url> findByAlgorithmAndEncodedUrl(Algorithm algorithm, String encodedUrl);

}
