package com.urlshortener.url.model.converter;

import com.urlshortener.url.model.dto.CreateResponse;
import com.urlshortener.url.model.entity.Url;
import org.springframework.stereotype.Component;

@Component
public class UrlConverter {
    public CreateResponse getCreateResponseFrom(Url url) {
        return new CreateResponse(url.getShortUrl(), url.getCount());
    }
}
