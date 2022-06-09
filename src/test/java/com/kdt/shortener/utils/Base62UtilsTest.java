package com.kdt.shortener.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class Base62UtilsTest {

    Base62Utils utils = new Base62Utils();

    @Test
    @DisplayName("Base62 인코딩/디코딩 테스트")
    void base62Test(){
        long originValue = 100007;

        long result = utils.decoding(utils.encoding(originValue));
        assertThat(result, is(originValue));
    }

}