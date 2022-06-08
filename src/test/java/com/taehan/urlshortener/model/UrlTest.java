package com.taehan.urlshortener.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UrlTest {
    @Test
    @DisplayName("count 증가 테스트")
    void testAddCount() {
        Url url = new Url("http://www.naver.com", "http://taehan/kkdsac2",  AlgorithmType.CUSTOM);
        url.addCount();

        assertThat(url.getCount()).isEqualTo(1);
    }

    // TO-DO: Validation 테스트
}