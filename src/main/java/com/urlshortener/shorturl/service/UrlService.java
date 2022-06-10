package com.urlshortener.shorturl.service;

import com.urlshortener.shorturl.model.dto.CreateRequest;
import com.urlshortener.shorturl.model.dto.CreateResponse;

public interface UrlService {
    CreateResponse save(CreateRequest request);

    String findOne(String shortUrl);
}
