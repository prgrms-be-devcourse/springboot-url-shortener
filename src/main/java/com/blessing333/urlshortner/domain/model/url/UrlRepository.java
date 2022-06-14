package com.blessing333.urlshortner.domain.model.url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

public interface UrlRepository extends JpaRepository<Url, Long> {
    @Query(value = "CALL NEXT VALUE FOR URL_SEQ;", nativeQuery = true)
    Long getNextValFromURLSequence();

    Url findByOriginalUrl(String originalUrl);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Url findUrlForUpdateById(String id);

    Url findById(String id);
}
