package com.programmers.urlshortener.util.encoder;

import lombok.Getter;

@Getter
public enum Algorithm {
    BASE62("base62"),
    SHA256("sha256");

    private final String name;

    Algorithm(String name) {
        this.name = name;
    }

    public static Algorithm from(String name) {
        for (Algorithm algorithm : Algorithm.values()) {
            if (algorithm.getName().equalsIgnoreCase(name)) {
                return algorithm;
            }
        }
        throw new IllegalArgumentException("제공되지 않는 URL 단축 알고리즘 : " + name);
    }

    public static UrlEncoder getUrlEncoder(Algorithm algorithm) {
        return switch (algorithm) {
            case BASE62 -> new Base62UrlEncoder();
            case SHA256 -> new Sha256UrlEncoder();
        };
    }
}
