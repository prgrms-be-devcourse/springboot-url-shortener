package org.prgms.springbooturlshortener.domain.shorturl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ShortUrl {
    @Id
    @Getter
    @Column(name = "transformed_url")
    private String transformedUrl;

    @Getter
    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "visit_count", nullable = false)
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
