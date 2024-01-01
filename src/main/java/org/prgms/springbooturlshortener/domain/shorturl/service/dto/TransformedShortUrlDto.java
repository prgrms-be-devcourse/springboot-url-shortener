package org.prgms.springbooturlshortener.domain.shorturl.service.dto;

import java.time.LocalDateTime;

public record TransformedShortUrlDto (String shortUrl,
                                      String originalUrl,
                                      Long visitCount,
                                      LocalDateTime lastVisitTime) {
}
