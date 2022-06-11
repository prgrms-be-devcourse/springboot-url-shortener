package prgrms.project.shorturl.dto;

import lombok.Builder;

public record ShortUrlResponse(
    String originUrl,
    String shortUrl,
    Long requestCount
) {

    @Builder
    public ShortUrlResponse {
    }
}
