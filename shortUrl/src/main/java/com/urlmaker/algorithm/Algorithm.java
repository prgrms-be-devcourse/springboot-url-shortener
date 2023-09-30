package com.urlmaker.algorithm;

public interface Algorithm {

    String encode(Long urlId);

    Long decode(String shortenUrl);

}
