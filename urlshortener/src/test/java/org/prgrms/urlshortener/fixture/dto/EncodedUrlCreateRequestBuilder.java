package org.prgrms.urlshortener.fixture.dto;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.dto.request.EncodedUrlCreateRequest;

public class EncodedUrlCreateRequestBuilder {

    public static EncodedUrlCreateRequest createOne(String originUrl) {
        return new EncodedUrlCreateRequest(originUrl, Algorithm.BASE_62);
    }

}
