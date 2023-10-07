package com.young.shortenerurl.url.model;

import com.young.shortenerurl.url.util.Base64EncoderV2;
import com.young.shortenerurl.url.util.Encoder;
import com.young.shortenerurl.url.util.Base64EncoderV1;

import java.util.function.Supplier;

public enum EncodingType {
    BASE_64_V1(Base64EncoderV1::new),
    BASE_64_V2(Base64EncoderV2::new);

    private final Supplier<Encoder> supplier;

    EncodingType(Supplier<Encoder> supplier) {
        this.supplier = supplier;
    }

    public Encoder getEncoder(){
        return supplier.get();
    }

}
