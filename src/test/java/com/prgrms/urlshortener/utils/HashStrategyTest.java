package com.prgrms.urlshortener.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HashStrategyTest {

    private HashStrategy hashStrategy;
    private final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @BeforeEach
    void setUp() {
        hashStrategy = new HashStrategy(BASE62);
    }

    @Test
    void shortenURL_ShouldReturnValidString() {
        String shortened = hashStrategy.shortenURL("https://www.example.com");

        assertNotNull(shortened);
        assertEquals(7, shortened.length());

        for (char c : shortened.toCharArray()) {
            assertTrue(BASE62.contains(String.valueOf(c)));
        }
    }
}