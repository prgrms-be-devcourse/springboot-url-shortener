package com.young.shortenerurl.application.dto;

import com.young.shortenerurl.model.EncodingType;

public record UrlCreateRequest(String originUrl, EncodingType encodingType) {
}
