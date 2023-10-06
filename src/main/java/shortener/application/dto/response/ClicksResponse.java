package shortener.application.dto.response;

public record ClicksResponse(
	String encodedUrl,
	Long clicks
) {

	public static ClicksResponse of(String encodedUrl, Long clicks) {
		return new ClicksResponse(encodedUrl, clicks);
	}
}
