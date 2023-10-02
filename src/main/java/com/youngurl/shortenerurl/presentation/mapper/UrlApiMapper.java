package com.youngurl.shortenerurl.presentation.mapper;

import com.youngurl.shortenerurl.application.dto.UrlCreateRequest;
import com.youngurl.shortenerurl.presentation.dto.UrlCreateApiRequest;
import org.springframework.stereotype.Component;

@Component
public class UrlApiMapper {

    public UrlCreateRequest toUrlCreateRequest(UrlCreateApiRequest request){
        return new UrlCreateRequest(
                request.originUrl(),
                request.encodingType());
    }

}
