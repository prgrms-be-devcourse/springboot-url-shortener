package prgrms.project.shorturl.global.converter;

import prgrms.project.shorturl.domain.ShortUrl;
import prgrms.project.shorturl.dto.ShortUrlRedirectResponse;
import prgrms.project.shorturl.dto.ShortUrlResponse;

public class ShortUrlConverter {

    public static ShortUrlResponse shortUrlResponseFrom(ShortUrl shortUrl) {

        return ShortUrlResponse
            .builder()
            .originUrl(shortUrl.getOriginUrl())
            .shortUrl(shortUrl.getShortUrl())
            .requestCount(shortUrl.getRequestCount())
            .build();
    }

    public static ShortUrlRedirectResponse shortUrlRedirectResponseFrom(ShortUrl shortUrl) {
        return new ShortUrlRedirectResponse(shortUrl.getOriginUrl(), shortUrl.getRequestCount());
    }
}
