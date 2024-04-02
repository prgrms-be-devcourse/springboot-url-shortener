package com.young.shortenerurl.url.infrastructures;

import com.young.shortenerurl.url.model.Url;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {

    @Query("SELECT u FROM Url u WHERE u.encodedUrl.encodedUrl = :encodedUrl")
    Optional<Url> findByEncodedUrl(@Param(value = "encodedUrl") String encodedUrl);

    Optional<Url> findUrlByOriginUrl(String originUrl);

    Boolean existsByOriginUrl(String originUrl);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE urls SET visit_count = visit_count + 1 WHERE id = :urlId", nativeQuery = true)
    void increaseVisitCount(Long urlId);
}
