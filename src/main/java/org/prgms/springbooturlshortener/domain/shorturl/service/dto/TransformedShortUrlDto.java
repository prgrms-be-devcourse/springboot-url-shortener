package org.prgms.springbooturlshortener.domain.shorturl.service.dto;

import java.time.LocalDateTime;

public record TransformedShortUrlDto (String shortUrl,
                                      Long visitCount,
                                      LocalDateTime lastVisitTime) {
}
