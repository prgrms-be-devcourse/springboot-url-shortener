package prgrms.project.shorturl.global;

import prgrms.project.shorturl.domain.ShortUrl;
import prgrms.project.shorturl.dto.ShortUrlRedirectResponse;
import prgrms.project.shorturl.dto.ShortUrlResponse;

public class ShortUrlConverter {

    public static ShortUrlResponse shortUrlResponseFrom(ShortUrl shortUrl) {
        return new ShortUrlResponse(shortUrl.getOriginUrl(), shortUrl.getShortenUrl(), shortUrl.getNumberOfRequests());
    }

    public static ShortUrlRedirectResponse shortUrlRedirectResponseFrom(ShortUrl shortUrl) {
        return new ShortUrlRedirectResponse(shortUrl.getOriginUrl(), shortUrl.getNumberOfRequests());
    }
}
