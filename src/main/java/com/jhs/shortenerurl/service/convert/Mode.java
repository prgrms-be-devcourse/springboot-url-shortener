package com.jhs.shortenerurl.service.convert;

public enum Mode {

    BASE_62(new Base62ShortenConverter());

    private final ShortenConverter converter;

    Mode(ShortenConverter converter) {
        this.converter = converter;
    }

    public String shorten(Long id) {
        return converter.convert(id);
    }

}
