package org.prgms.springbooturlshortener.domain.shorturl;

import jakarta.persistence.Column;
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
    @Column(name = "transformed_url")
    private String transformedUrl;

    @Column(name = "original_url")
    private String originalUrl;

    @Column(name = "visit_count")
    private Long visitCount;

    @Column(name = "last_visit_time")
    private LocalDateTime lastVisitTime;

    @Builder
    public ShortUrl(String transformedUrl, String originalUrl) {
        this.transformedUrl = transformedUrl;
        this.originalUrl = originalUrl;
        this.visitCount = 0L;
    }

    public void increaseVisit() {
        this.visitCount++;
    }
}
