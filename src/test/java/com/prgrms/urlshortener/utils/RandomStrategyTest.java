package com.prgrms.urlshortener.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomStrategyTest {

    private RandomStrategy randomStrategy;
    private final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @BeforeEach
    void setUp() {
        randomStrategy = new RandomStrategy(BASE62);
    }

    @Test
    void shortenURL_ShouldReturnValidString() {
        String shortened = randomStrategy.shortenURL("https://www.example.com");

        assertNotNull(shortened);
        assertEquals(7, shortened.length());

        for (char c : shortened.toCharArray()) {
            assertTrue(BASE62.contains(String.valueOf(c)));
        }
    }
}