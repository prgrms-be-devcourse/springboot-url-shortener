package com.programmers.springbooturlshortener.domain.entity;


import com.programmers.springbooturlshortener.domain.infrastructure.utils.Base58;
import com.programmers.springbooturlshortener.domain.infrastructure.utils.Base62;

import java.util.function.UnaryOperator;

public enum EncodeType {
    BASE62(Base62::encode), BASE58(Base58::encode);
    private final UnaryOperator<String> function;

    EncodeType(UnaryOperator<String> function) {
        this.function = function;
    }

    public String encode(String url) {
        return function.apply(url);
    }
}
