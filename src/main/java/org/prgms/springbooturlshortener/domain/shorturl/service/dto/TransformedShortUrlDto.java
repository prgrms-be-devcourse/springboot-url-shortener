package org.prgms.springbooturlshortener.domain.shorturl.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public record TransformedShortUrlDto (String shortUrl,
                                      Long visitCount,
                                      LocalDateTime lastVisitTime) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransformedShortUrlDto that = (TransformedShortUrlDto) o;
        return Objects.equals(shortUrl, that.shortUrl) && Objects.equals(visitCount, that.visitCount) && Objects.equals(lastVisitTime, that.lastVisitTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrl, visitCount, lastVisitTime);
    }
}
