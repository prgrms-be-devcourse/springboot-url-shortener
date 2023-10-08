package com.tangerine.urlshortener.url.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import org.springframework.util.Assert;

@Embeddable
public class OriginUrl {

    @NotBlank
    @Column(name = "origin_url", nullable = false, unique = true)
    private String originUrlText;

    public OriginUrl(String originUrlText) {
        Assert.isTrue(StringUtils.isNotBlank(originUrlText), "원본 url은 반드시 주어져야 합니다.");
        this.originUrlText = originUrlText;
    }

    protected OriginUrl() {
    }

    public String getOriginUrlText() {
        return originUrlText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OriginUrl originUrl = (OriginUrl) o;
        return Objects.equals(originUrlText, originUrl.originUrlText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originUrlText);
    }

    public String redirect() {
        return "redirect:%s".formatted(getOriginUrlText());
    }
}
