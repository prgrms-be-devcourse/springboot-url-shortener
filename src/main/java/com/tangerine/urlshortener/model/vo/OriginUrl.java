package com.tangerine.urlshortener.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
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
}
