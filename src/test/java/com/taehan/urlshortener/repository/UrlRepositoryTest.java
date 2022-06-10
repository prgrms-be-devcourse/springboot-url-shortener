package com.taehan.urlshortener.repository;

import com.taehan.urlshortener.model.AlgorithmType;
import com.taehan.urlshortener.model.Url;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

    private final Url url = new Url("url.com", "ss",  AlgorithmType.BASE62);

    @Test
    @DisplayName("findById 성공")
    void testFindById() {
        urlRepository.save(url);

        Optional<Url> findUrl = urlRepository.findById(url.getId());
        assertAll(
                () -> assertThat(findUrl).isPresent(),
                () -> assertThat(findUrl.get().getId()).isEqualTo(url.getId())
        );
    }

    @Test
    @DisplayName("findAll 성공")
    void testFindAll() {
        Url url2 = new Url("url2.com", "ss2",  AlgorithmType.BASE62);

        urlRepository.save(url);
        urlRepository.save(url2);

        List<Url> urlList = urlRepository.findAll();
        assertAll(
                () -> assertThat(urlList).hasSize(2),
                () -> assertThat(urlList).contains(url),
                () -> assertThat(urlList).contains(url2)
        );
    }

    @Test
    @DisplayName("findByShortUrl 성공")
    void testFindByShortUrl() {
        urlRepository.save(url);

        Optional<Url> findUrl = urlRepository.findByShortUrl(url.getShortUrl());
        String originalUrl = findUrl.get().getOriginalUrl();

        assertThat(originalUrl).isEqualTo(url.getOriginalUrl());
    }
}