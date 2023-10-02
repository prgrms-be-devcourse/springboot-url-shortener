package com.youngurl.shortenerurl.model;

public interface Encoder {
    String encode(long index);

    long decode(String param);
}
