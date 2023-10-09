package com.tangerine.urlshortener.url.algorithm;

public interface BaseAlgorithm {

    String encode(Long mappingId);

    long decode(String shortUrl);

}
