package com.prgrms.urlshortener.domain.url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UrlRepositoryTest {
    @Autowired
    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        urlRepository.save(new Url("abc.com", 0));
    }

    @DisplayName("findByUrl 메서드 테스트")
    @Test
    void testFindByUrl() {
        Optional<Url> mayBeExistedUrl = urlRepository.findByUrl("abc.com");
        assertThat(mayBeExistedUrl).isNotEmpty();
    }
}