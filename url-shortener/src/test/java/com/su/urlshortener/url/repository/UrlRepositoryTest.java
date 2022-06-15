package com.su.urlshortener.url.repository;

import com.su.urlshortener.url.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

}