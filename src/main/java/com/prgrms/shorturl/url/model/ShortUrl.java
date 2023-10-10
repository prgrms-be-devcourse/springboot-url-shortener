package com.prgrms.shorturl.url.model;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortUrl {

    public static final int SHORT_URL_MAX_LENGTH = 8;

    private String shortUrl;

    public ShortUrl(String shortUrl) {
        isNotBlank(shortUrl);
        isLimitLength(shortUrl);

        this.shortUrl = shortUrl;
    }

    private void isNotBlank(String shortUrl) {
        if (shortUrl == null || shortUrl.isBlank()) {
            throw new IllegalArgumentException("Short URL은 null이거나 빈 값일 수 없습니다.");
        }
    }

    private void isLimitLength(String shortUrl) {
        if (shortUrl.length() > SHORT_URL_MAX_LENGTH) {
            throw new IllegalArgumentException("Short URL의 길이는" + SHORT_URL_MAX_LENGTH + "를 넘을 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortUrl shortUrl1 = (ShortUrl) o;
        return Objects.equals(shortUrl, shortUrl1.shortUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrl);
    }

}
