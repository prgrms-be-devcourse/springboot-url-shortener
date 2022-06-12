package com.prgrms.urlshortener.service.encoder;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UrlEncoders {

    private final List<UrlEncoder> urlEncoders;

    public UrlEncoders(List<UrlEncoder> urlEncoders) {
        this.urlEncoders = urlEncoders;
    }

    public UrlEncoder getUrlEncoderByType(String encoderType) {
        return urlEncoders.stream()
            .filter(encoder -> encoder.supports(encoderType))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(encoderType + "은 지원하는 알고리즘이 아닙니다."));
    }

}
