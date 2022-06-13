package com.prgrms.urlshortener.service.encoder;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prgrms.urlshortener.exception.InvalidEncodeTypeException;

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
            .orElseThrow(() -> new InvalidEncodeTypeException(encoderType));
    }

}
