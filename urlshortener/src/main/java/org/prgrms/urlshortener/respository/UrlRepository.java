package org.prgrms.urlshortener.respository;

import org.prgrms.urlshortener.domain.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
