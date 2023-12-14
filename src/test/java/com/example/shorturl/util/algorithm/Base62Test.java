package com.example.shorturl.util.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Base62Test {

    @Test
    void base62_인코딩_테스트() {
        // Given
        Base62 base62 = new Base62();
        Long sequence = 12345L;

        // When
        String result = base62.encodeUrl(sequence);

        // Then
        assertEquals("DNH", result);
    }
}
