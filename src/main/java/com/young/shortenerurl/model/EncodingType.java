package com.young.shortenerurl.model;

import java.util.function.Supplier;

public enum EncodingType {
    BASE_62(Base62Encoder::new);

    private final Supplier<Encoder> supplier;

    EncodingType(Supplier<Encoder> supplier) {
        this.supplier = supplier;
    }

    public Encoder getEncoder(){
        return supplier.get();
    }

}
