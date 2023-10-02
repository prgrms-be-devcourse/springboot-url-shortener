package com.prgrms.shorturl.algorithm;

public interface Algorithm {

    String encode(Long id);

    Long decode(String shortUrl);
}
