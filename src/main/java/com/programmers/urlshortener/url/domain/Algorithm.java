package com.programmers.urlshortener.url.domain;

import com.programmers.urlshortener.common.converter.Base62Converter;

public enum Algorithm {
    BASE62 {
        public String getShortUrl(int num) {
            return Base62Converter.encode(num);
        }
    };

    public abstract String getShortUrl(int num);
}
