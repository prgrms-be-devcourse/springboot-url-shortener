package com.programmers.springbooturlshortener.domain.entity;


import com.programmers.springbooturlshortener.domain.infrastructure.utils.Base62;
import com.programmers.springbooturlshortener.domain.infrastructure.utils.Encoder;

public enum EncodeType {
    BASE62(new Base62());
    private final Encoder encoder;

    EncodeType(Encoder encoder) {
        this.encoder = encoder;
    }

    public String encode(String url) {
        return encoder.encode(url);
    }
}
