package prgms.marco.springbooturlshortener.dto;

import org.hibernate.validator.constraints.URL;

public class CreateShortUrlReq {

    @URL
    private String originUrl;

    public CreateShortUrlReq() {

    }

    public CreateShortUrlReq(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getOriginUrl() {
        return originUrl;
    }
}
