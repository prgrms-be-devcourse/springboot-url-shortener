package com.urlshortener.url.model.dto;

import lombok.Data;

@Data
public class CreateResponse {
    private final String shortUrl;
    private final Integer count;
}
