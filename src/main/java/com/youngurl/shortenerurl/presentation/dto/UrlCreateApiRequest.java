package com.youngurl.shortenerurl.presentation.dto;

import com.youngurl.shortenerurl.model.EncodingType;

public record UrlCreateApiRequest(String originUrl, EncodingType encodingType) {
}
