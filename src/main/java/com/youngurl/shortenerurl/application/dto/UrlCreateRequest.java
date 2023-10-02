package com.youngurl.shortenerurl.application.dto;

import com.youngurl.shortenerurl.model.EncodingType;

public record UrlCreateRequest(String originUrl, EncodingType encodingType) {
}
