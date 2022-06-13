package com.prgms.shorturl.url.repository;

import static org.assertj.core.api.Assertions.*;

import com.prgms.shorturl.url.domain.Url;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UrlRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("url 저장 성공")
    void saveTest() {
        // Given
        Url url = new Url("https://programmers.co.kr/");

        // When
        urlRepository.save(url);

        em.flush();
        em.clear();

        // Then
        Optional<Url> findUrl = urlRepository.findById(url.getId());
        assertThat(findUrl.isPresent()).isTrue();
    }
    
//    @Test
//    @DisplayName("")
//    void () {
//        // Given
//
//        // When
//
//        // Then
//    }

}