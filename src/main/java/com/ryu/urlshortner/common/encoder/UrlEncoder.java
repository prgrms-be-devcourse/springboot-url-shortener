package com.ryu.urlshortner.common.encoder;

public interface UrlEncoder {
    String encode(long seq);
    long decode(String shortUrl);
}
