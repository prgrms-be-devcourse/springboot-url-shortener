package org.prgms.springbooturlshortener.domain.shorturl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ShortUrl {
    @Id
    @Getter
    private String transformedUrl;

    private String originalUrl;

    private Long visitCount;

    private LocalDateTime lastVisitTime;

    @Builder
    public ShortUrl(String transformedUrl, String originalUrl) {
        this.transformedUrl = transformedUrl;
        this.originalUrl = originalUrl;
        this.visitCount = 0L;
    }
}
