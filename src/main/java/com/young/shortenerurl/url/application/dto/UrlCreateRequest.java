package com.young.shortenerurl.url.application.dto;

import com.young.shortenerurl.url.model.EncodingType;

public record UrlCreateRequest(String originUrl, EncodingType encodingType) {
}
