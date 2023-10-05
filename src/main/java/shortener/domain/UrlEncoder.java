package shortener.domain;

public interface UrlEncoder {
	String encode(ShortUrl shortUrl, String url);
}
