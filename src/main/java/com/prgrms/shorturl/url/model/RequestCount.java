package com.prgrms.shorturl.url.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCount {

    private static final int REQUEST_COUNT_MIN_LIMIT = 0;

    private int requestCount;

    public RequestCount(int requestCount) {
        isValidated(requestCount);
        this.requestCount = requestCount;
    }

    private void isValidated(int requestCount) {
        if (requestCount < REQUEST_COUNT_MIN_LIMIT) {
            throw new IllegalArgumentException("요청 횟수는 음수가 나올 수 없습니다.");
        }
    }

    public void plusOneCount() {
        requestCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestCount that = (RequestCount) o;
        return Objects.equals(requestCount, that.requestCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestCount);
    }

}
