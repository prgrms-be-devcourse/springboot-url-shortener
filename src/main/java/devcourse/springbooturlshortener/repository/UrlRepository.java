package devcourse.springbooturlshortener.repository;

import devcourse.springbooturlshortener.entity.Url;
import devcourse.springbooturlshortener.entity.vo.OriginalUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByShortUrl(String shortUrl);

    Optional<Url> findByOriginalUrl(OriginalUrl originalUrl);

    @Modifying
    @Query("update Url u set u.hit = u.hit + 1 where u.id = :id")
    void increaseHit(Long id);
}
