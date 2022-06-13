package com.prgms.shorturl.url.repository;

import com.prgms.shorturl.url.domain.Url;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByLongUrl(String url);

}
