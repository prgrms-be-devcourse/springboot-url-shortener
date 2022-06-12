package com.prgrms.urlshortener.service.encoder;

public interface UrlEncoder {

    String encode(long id);

    boolean resolve(String encodedType);

}
