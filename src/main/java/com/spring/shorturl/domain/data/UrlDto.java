package com.spring.shorturl.domain.data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UrlDto {
    public record SaveRequest(
            @NotEmpty @Size(max = 500)
            String originUrl
    ) {
    }

    public record ShortUrlRequest(
            @NotEmpty @Size(max = 100)
            String shortUrl
    ){

    }

    public record Response(
            String shortUrl
    ) {
    }
}
