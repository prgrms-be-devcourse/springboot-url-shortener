package com.example.urlshortenert2.service;

import com.example.urlshortenert2.ShorteningKeyMaker.MyShorteningKeyMaker;
import com.example.urlshortenert2.ShorteningKeyMaker.ShorteningKeyMaker;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class ShorteningKeyMakerTest {

    ShorteningKeyMaker myShorteningKeyMaker = new MyShorteningKeyMaker();

    @Test
    @DisplayName("베이스62 구현 테스트")
    public void base62_구현_테스트() throws Exception {
        // given
        Long id = 1L;
        // when
        String shorteningKey = myShorteningKeyMaker.makeShorteningKey(id);
        // then
        Assertions.assertThat(shorteningKey).isEqualTo("B+++++++");
    }
}