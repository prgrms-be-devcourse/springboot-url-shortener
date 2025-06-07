package com.tangerine.urlshortener.url.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

@Embeddable
public class ShortUrl {

    private static final int URL_LENGTH_BOUND = 8;

    @NotBlank
    @Length(min = 1, max = URL_LENGTH_BOUND)
    @Column(name = "short_url", unique = true)
    private String shortUrlText;

    public ShortUrl(String shortUrlText) {
        Assert.isTrue(StringUtils.isNotBlank(shortUrlText), "단축 url은 반드시 주어져야 합니다.");
        Assert.isTrue(shortUrlText.length() <= URL_LENGTH_BOUND, "단축 url은 8자 이하여야 합니다.");
        this.shortUrlText = shortUrlText;
    }

    public ShortUrl() {
    }

    public String getShortUrlText() {
        return shortUrlText;
    }

    public boolean isEmptyShortUrl() {
        return shortUrlText == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShortUrl shortUrl = (ShortUrl) o;
        return Objects.equals(shortUrlText, shortUrl.shortUrlText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrlText);
    }
}
