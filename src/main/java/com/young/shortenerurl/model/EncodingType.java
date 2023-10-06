package com.young.shortenerurl.model;

import com.young.shortenerurl.model.encoder.Base62Encoder;
import com.young.shortenerurl.model.encoder.Encoder;
import com.young.shortenerurl.model.encoder.UrlSafeBase64Encoder;

import java.util.function.Supplier;

public enum EncodingType {
    BASE_62(Base62Encoder::new),
    URL_SAFE_BASE_64(UrlSafeBase64Encoder::new);

    private final Supplier<Encoder> supplier;

    EncodingType(Supplier<Encoder> supplier) {
        this.supplier = supplier;
    }

    public Encoder getEncoder(){
        return supplier.get();
    }

}
