package com.su.urlshortener.url.service.shortener;

import org.junit.jupiter.api.Test;

import static com.su.urlshortener.url.service.shortener.ShorteningAlgorithm.RANDOM;
import static com.su.urlshortener.url.service.shortener.ShorteningAlgorithm.SEQUENCE;

class ShorteningAlgorithmTest {

    String givenUrl = "https://www.google.com/";

    @Test
    void sequence() {
        var token = SEQUENCE.makeShorteningKey(givenUrl);
        System.out.println(token);
    }

    @Test
    void random() {
        var token = RANDOM.makeShorteningKey(givenUrl);
        System.out.println(token);
    }

}