package com.prgrms.shorturl.url.controller.mapper;

import com.prgrms.shorturl.url.controller.dto.ShortUrlCreateApiResponse;
import com.prgrms.shorturl.url.controller.dto.ShortUrlFindApiResponse;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlApiMapper {

    public ShortUrlCreateApiResponse toShortUrlCreateApiResponse(String shortUrl) {
        return new ShortUrlCreateApiResponse(shortUrl);
    }

    public ShortUrlFindApiResponse toShortUrlFindApiResponse(String originUrl) {
        return new ShortUrlFindApiResponse(originUrl);
    }

}
