package com.tangerine.urlshortener.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.Assert;

@Embeddable
public class ShortUrl {

    @NotBlank
    @Column(name = "short_url", nullable = false, unique = true)
    private String shortUrlText;

    public ShortUrl(String shortUrlText) {
        Assert.isTrue(StringUtils.isNotBlank(shortUrlText), "단축 url은 반드시 주어져야 합니다.");
        Assert.isTrue(shortUrlText.length() <= 8, "단축 url은 8자 이하여야 합니다.");
        this.shortUrlText = shortUrlText;
    }

    protected ShortUrl() {
    }

    public String getShortUrlText() {
        return shortUrlText;
    }
}
