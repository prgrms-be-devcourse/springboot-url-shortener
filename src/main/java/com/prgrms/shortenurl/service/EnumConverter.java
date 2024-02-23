package com.prgrms.shortenurl.service;

import java.util.HashMap;
import java.util.Map;

public class EnumConverter {
    private static final Map<String, EncodingType> stringToEnumMap = new HashMap<>();

    static {
        stringToEnumMap.put("Base62", EncodingType.Base62);
        stringToEnumMap.put("MurMur", EncodingType.Murmur);
    }

    public static EncodingType convert(String value) {
        return stringToEnumMap.get(value);
    }
}
