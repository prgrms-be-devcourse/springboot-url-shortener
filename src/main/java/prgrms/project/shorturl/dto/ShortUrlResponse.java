package prgrms.project.shorturl.dto;

public record ShortUrlResponse(
	String originUrl,
	String shortUrl,
	int requestCount
) {

}
