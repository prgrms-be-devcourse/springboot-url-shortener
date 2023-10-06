package com.tangerine.urlshortener.url.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import java.util.Objects;
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

    public static RequestCount addCount(RequestCount requestCount) {
        return new RequestCount(requestCount.getRequestCount() + 1);
    }

    public long getRequestCount() {
        return requestCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestCount that = (RequestCount) o;
        return requestCount == that.requestCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestCount);
    }
}
