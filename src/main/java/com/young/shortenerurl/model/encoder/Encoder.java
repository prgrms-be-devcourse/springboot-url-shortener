package com.young.shortenerurl.model.encoder;

public abstract class Encoder {
    protected final Integer MAX_LENGTH = 8;

    protected Encoder() {
    }

    public abstract String encode(long index);
}
