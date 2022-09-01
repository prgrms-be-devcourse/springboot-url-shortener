package prgrms.project.shorturl.dto;

public record ShortUrlRedirectResponse(
    String originUrl,
    int requestCount
) {
}
