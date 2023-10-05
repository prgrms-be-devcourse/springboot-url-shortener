package com.tangerine.urlshortener.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import org.springframework.util.Assert;

@Embeddable
public class RequestCount {

    @Positive
    @Column(name = "request_count", nullable = false)
    private long requestCount;

    public RequestCount(long requestCount) {
        Assert.isTrue(requestCount >= 0, "호출 횟수는 양수여야 합니다.");
        this.requestCount = requestCount;
    }

    protected RequestCount() {
    }

    public long getRequestCount() {
        return requestCount;
    }
}
