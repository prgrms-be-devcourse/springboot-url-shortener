package com.young.shortenerurl.url.application.dto;

import com.young.shortenerurl.url.model.EncodedUrl;
import com.young.shortenerurl.url.model.EncodingType;
import com.young.shortenerurl.url.model.Url;

public record UrlCreateRequest(
        String originUrl,
        EncodingType encodingType
) {
    public Url toUrl(String encodedUrl) {
        return new Url(
                originUrl,
                new EncodedUrl(encodedUrl, encodingType));
    }
}
