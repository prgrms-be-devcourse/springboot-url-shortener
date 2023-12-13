package org.prgms.springbooturlshortener.domain.shorturl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ShortUrl {
    @Id
    private String transformedUrl;

    private String originalUrl;

    private Long visitCount;

    private LocalDateTime lastVisitTime;
}
