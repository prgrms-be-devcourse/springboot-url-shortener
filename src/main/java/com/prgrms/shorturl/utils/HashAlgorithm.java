package com.prgrms.shorturl.utils;

import lombok.Getter;

@Getter
public enum HashAlgorithm {
    SHA_256("SHA-256");

    private final String name;
    HashAlgorithm(String name) {
        this.name = name;
    }
}
