package com.urlshortener.shorturl.model.converter;

import com.urlshortener.shorturl.model.dto.CreateResponse;
import com.urlshortener.shorturl.model.entity.Url;
import org.springframework.stereotype.Component;

@Component
public class UrlConverter {
    public CreateResponse getCreateResponseFrom(Url url) {
        return new CreateResponse(url.getShortUrl(), url.getCount());
    }
}
