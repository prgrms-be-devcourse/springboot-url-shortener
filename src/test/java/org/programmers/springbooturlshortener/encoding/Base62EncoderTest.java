package org.programmers.springbooturlshortener.encoding;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;

class Base62EncoderTest {

    static Base62Encoder encoder = new Base62Encoder();

    @ParameterizedTest
    @MethodSource("encodeParams")
    void encode(Long id, String result) {
        assertThat(encoder.encode(id)).isEqualTo(result);
    }

    /*
    1,     2,       3,        4,         5        ,   6,            7                   8
    62,    3844,    238328,   14776336,  916132832,   56800235584,  3 5216 1460 6208    218 3401 0558 4896
                              1477만     9억1613만     568억23만      3조 5216억           218조 3401억
        long
        922 3372 0368 5477 5807
     */

    private static Stream<Arguments> encodeParams() {
        return Stream.of(
                arguments(1L,                   "1"),
                arguments(61L,                  "z"),
                arguments(12395432L,            "Kc0q"),
                arguments(7952117461L,          "31HAg8"),
                arguments(56800235584L,         "0000001"),
                arguments(3521614606207L,       "zzzzzzz"),
                arguments(3521614606208L,       "00000001"),
                arguments(218340105584895L,     "zzzzzzzz")
        );
    }
}