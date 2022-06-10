package prgms.marco.springbooturlshortener.dto;

public class CreateShortUrlRes {
    private String shortUrl;

    public CreateShortUrlRes() {
    }

    public CreateShortUrlRes(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
