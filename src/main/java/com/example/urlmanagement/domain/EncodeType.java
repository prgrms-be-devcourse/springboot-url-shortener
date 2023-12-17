package com.example.urlmanagement.domain;

import com.example.urlmanagement.exception.UrlErrorCode;
import com.example.urlmanagement.exception.UrlException;

import java.util.Arrays;

public enum EncodeType {

    BASE58,
    BASE62;

    public static EncodeType getEncodeTypeByName(String name) {
        return Arrays.stream(EncodeType.values())
                .filter(encodeType -> encodeType.getName().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new UrlException(UrlErrorCode.ENCODE_TYPE_NOT_FOUND, name));
    }

    private String getName() {
        return this.name();
    }
}
