package com.young.shortenerurl.url.presentation.mapper;

import com.young.shortenerurl.url.application.dto.UrlCreateRequest;
import com.young.shortenerurl.url.presentation.dto.UrlCreateApiRequest;
import org.springframework.stereotype.Component;

@Component
public class UrlApiMapper {

    public UrlCreateRequest toUrlCreateRequest(UrlCreateApiRequest request) {
        return new UrlCreateRequest(
                request.originUrl(),
                request.encodingType());
    }

}
