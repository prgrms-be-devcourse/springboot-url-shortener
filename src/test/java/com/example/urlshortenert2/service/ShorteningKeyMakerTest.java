package com.example.urlshortenert2.service;

import com.example.urlshortenert2.ShorteningKeyMaker.Base62LibraryKeyMaker;
import com.example.urlshortenert2.ShorteningKeyMaker.ShorteningKeyMaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShorteningKeyMakerTest {

    ShorteningKeyMaker shorteningKeyMaker = new Base62LibraryKeyMaker();
    @Test
    @DisplayName("베이스62테스트")
    public void 베이스62테스트() throws Exception {
        //given
        String base62 = shorteningKeyMaker.makeShorteningKey(52454245634552341L);
        System.out.println(base62);
        base62 = shorteningKeyMaker.makeShorteningKey(24556463L);
        System.out.println(base62);
        //when

        //then
    }
}