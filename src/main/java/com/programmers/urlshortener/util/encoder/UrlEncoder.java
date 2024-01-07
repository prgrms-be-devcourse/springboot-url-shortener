package com.programmers.urlshortener.util.encoder;

public interface UrlEncoder {
    String encode(String url);
    Algorithm getAlgorithm();
}
