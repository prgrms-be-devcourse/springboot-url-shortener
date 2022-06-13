package org.programmers.springbooturlshortener.encoding;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

public enum Encoding {
    BASE62('1', new Base62Encoder(), "BASE62");

    Encoding(char typePrefix, Encoder encoder, String name) {
        this.typePrefix = typePrefix;
        this.encoder = encoder;
        this.name = name;
    }

    @Value("${url-shortener.base-url}")
    private static final String COMMON_PREFIX = "localhost:8080/";
    private final char typePrefix;
    private final Encoder encoder;
    @Getter
    private final String name;

    public String encode(Long key) {
        return COMMON_PREFIX + typePrefix + encoder.encode(key);
    }

    static Encoding ofShortenUrl(String shortenUrl) {
        char encodingCode = shortenUrl.charAt(0);
        Encoding[] encodings = Encoding.values();
        for (Encoding encoding : encodings) {
            if (encodingCode == encoding.typePrefix) {
                return encoding;
            }
        }
        throw new NoSuchEncodingException(encodingCode);
    }

    public static String getOriginalUrlKey(String shortenUrl) {
        Encoding encoding = ofShortenUrl(shortenUrl);
        String key = shortenUrl.substring(1);
        if (!StringUtils.hasText(key) || !encoding.encoder.charset.contains(key)) {
            throw new IllegalUrlException(shortenUrl);
        }
        return key;
    }
}