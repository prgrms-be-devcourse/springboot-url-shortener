package shortener.application.dto.response;

import shortener.domain.ShortUrl;

public record ShortUrlCreateResponse(
	Long id,
	String shortUrl,
	String originalUrl
) {

	public static ShortUrlCreateResponse of(ShortUrl newShortUrl) {
		Long shortUrlId = newShortUrl.getId();
		String shortUrl = newShortUrl.getEncodedUrl();
		String originalUrl = newShortUrl.getOriginalUrl();

		return new ShortUrlCreateResponse(shortUrlId, shortUrl, originalUrl);
	}
}
