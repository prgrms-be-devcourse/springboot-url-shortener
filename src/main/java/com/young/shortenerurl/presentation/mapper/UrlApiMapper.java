package com.young.shortenerurl.presentation.mapper;

import com.young.shortenerurl.application.dto.UrlCreateRequest;
import com.young.shortenerurl.presentation.dto.UrlCreateApiRequest;
import org.springframework.stereotype.Component;

@Component
public class UrlApiMapper {

    public UrlCreateRequest toUrlCreateRequest(UrlCreateApiRequest request){
        return new UrlCreateRequest(
                request.originUrl(),
                request.encodingType());
    }

}
