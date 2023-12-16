package com.programmers.urlshortener.util.encoder;

public class Sha256UrlEncoder implements UrlEncoder {
    @Override
    public String encode(String url) {
        //TODO
        return null;
    }

    @Override
    public Algorithm getAlgorithm() {
        return Algorithm.SHA256;
    }
}
