package org.prgrms.urlshortener.fixture.dto;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.dto.request.OriginUrlRequest;

public class OriginUrlRequestBuilder {

    public static OriginUrlRequest createOne(String shortUrl) {
        return new OriginUrlRequest(shortUrl, Algorithm.BASE_62);
    }

}
