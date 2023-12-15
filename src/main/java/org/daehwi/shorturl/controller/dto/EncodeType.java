package org.daehwi.shorturl.controller.dto;

public enum EncodeType {
    BASE62(0), BASE32(1);

    private final int code;

    EncodeType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
