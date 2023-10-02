package com.young.shortenerurl.infrastructures;

import com.young.shortenerurl.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlJpaRepository extends JpaRepository<Url, Long> {

    @Query("SELECT u FROM Url u WHERE u.encodedUrl.encodedUrl = :encodedUrl")
    Optional<Url> findByEncodedUrl(@Param(value = "encodedUrl") String encodedUrl);

}
