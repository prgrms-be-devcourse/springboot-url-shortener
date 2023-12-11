package kdt.shorturl.url.domain;

import kdt.shorturl.grobal.util.Base62Converter;

public enum Algorithm {

    BASE62 {
        @Override
        public String getShortUrl(int num) {
            return Base62Converter.encoding(num);
        }
    };

    public abstract String getShortUrl(int num);
}
