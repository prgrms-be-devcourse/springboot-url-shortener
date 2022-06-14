package com.urlshortener.url.service;

import com.urlshortener.url.model.dto.CreateRequest;
import com.urlshortener.url.model.dto.CreateResponse;

public interface UrlService {
    CreateResponse register(CreateRequest request);

    String findOne(String shortUrl);
}
