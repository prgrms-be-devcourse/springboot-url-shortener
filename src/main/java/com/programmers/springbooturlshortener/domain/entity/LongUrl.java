package com.programmers.springbooturlshortener.domain.entity;

import com.programmers.springbooturlshortener.domain.exception.LongUrlFormatMismatchException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class LongUrl {
    private static final Pattern PATTERN = Pattern.compile("[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?");
    private final String value;

    private LongUrl(String value) {
        this.value = value;
    }

    public static LongUrl from(String longUrl) {
        if (!PATTERN.matcher(longUrl).matches()) {
            throw new LongUrlFormatMismatchException();
        }
        return new LongUrl(longUrl);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongUrl longUrl = (LongUrl) o;
        return Objects.equals(value, longUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
