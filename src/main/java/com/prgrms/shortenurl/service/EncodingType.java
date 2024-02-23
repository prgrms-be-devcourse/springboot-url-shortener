package com.prgrms.shortenurl.service;

import com.prgrms.shortenurl.domain.Url;

import java.util.function.Function;

public enum EncodingType {
    Base62("Base62", UrlService::shortenUrlByBase62),
    Murmur("MurMur", UrlService::shortenUrlByMurMur);

    private final String name;
    private final Function<Url, String> encoder;

    EncodingType(String name, Function<Url, String> encoder) {
        this.name = name;
        this.encoder = encoder;
    }

    public String encode(Url url) {
        return encoder.apply(url);
    }
}
