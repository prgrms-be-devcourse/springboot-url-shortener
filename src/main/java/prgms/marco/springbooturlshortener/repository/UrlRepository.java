package prgms.marco.springbooturlshortener.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import prgms.marco.springbooturlshortener.entity.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByOriginUrl(String originUrl);
}
