package com.taehan.urlshortener.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTypeTest {

    @Test
    void test() {
        String convert = AlgorithmType.BASE62.convert("hanju");
        System.out.println(convert);
    }


}