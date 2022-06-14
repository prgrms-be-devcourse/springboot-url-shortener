package com.blessing333.urlshortner.domain.model.url;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@Rollback(value = false)
class UrlRepositoryTest {
    @Autowired
    private UrlRepository repository;

    @DisplayName("비관적 락 테스트")
    @Test
    void testPessimisticLock() {
        String id = "aaaaaaa";
        Url url = Url.createNewUrl(id, "https://naver.com", "http://localhost/aaaaaaa");

        repository.save(url);

        repository.findUrlForUpdateById(id);
        repository.deleteAll();
    }
}