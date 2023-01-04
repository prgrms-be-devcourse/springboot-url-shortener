package org.prgrms.url.shortner.springbooturlshortner.domain.repository;

import java.util.Optional;

import org.prgrms.url.shortner.springbooturlshortner.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {

	Optional<Url> findUrlByShortenUrlEquals(@Param("shortenUrl") String shortenUrl);

}
