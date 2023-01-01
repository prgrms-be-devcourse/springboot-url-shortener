package org.prgrms.url.shortner.springbooturlshortner.domain.repository;

import org.prgrms.url.shortner.springbooturlshortner.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {
}
