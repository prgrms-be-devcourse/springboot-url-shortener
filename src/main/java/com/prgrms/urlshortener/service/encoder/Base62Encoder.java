package com.prgrms.urlshortener.service.encoder;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder implements UrlEncoder {

    @Override
    public String encode(long id) {
        return Base62Utils.encode(id);
    }

    @Override
    public boolean resolve(String encodedType) {
        return EncodeType.BASE62.toString().equals(encodedType);
    }

}
