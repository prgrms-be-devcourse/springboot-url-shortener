package com.example.urlshortenert2.service;

import com.example.urlshortenert2.ShorteningKeyMaker.Base62LibraryKeyMaker;
import com.example.urlshortenert2.ShorteningKeyMaker.MyShorteningKeyMaker;
import com.example.urlshortenert2.ShorteningKeyMaker.ShorteningKeyMaker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class ShorteningKeyMakerTest {

    ShorteningKeyMaker shorteningKeyMaker = new Base62LibraryKeyMaker();
    ShorteningKeyMaker myShorteningKeyMaker = new MyShorteningKeyMaker();
    @Test
    @DisplayName("베이스62테스트")
    public void base62테스트() throws Exception {
        String base62 = shorteningKeyMaker.makeShorteningKey(52454245634552341L);
        System.out.println(base62);
        base62 = shorteningKeyMaker.makeShorteningKey(24556463L);
        System.out.println(base62);
    }

    @Test
    @DisplayName("베이스62 구현 테스트")
    public void base62_구현_테스트() throws Exception {
        String base62 = myShorteningKeyMaker.makeShorteningKey(5245424563441L);
        System.out.println("test1 - " + base62);
        base62 = myShorteningKeyMaker.makeShorteningKey(24556463L);
        System.out.println("test2 - " + base62);
    }
}