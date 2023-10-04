package com.tangerine.urlshortener;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.util.Assert;

@Embeddable
public class Algorithm {

    @NotBlank
    @Column(name = "algorithm", nullable = false)
    private String algorithm;

    public Algorithm(String algorithm) {
        Assert.isTrue(StringUtils.isNotBlank(algorithm), "단축 Url 알고리즘은 반드시 주어져야 합니다.");
        this.algorithm = algorithm;
    }

    protected Algorithm() {
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
