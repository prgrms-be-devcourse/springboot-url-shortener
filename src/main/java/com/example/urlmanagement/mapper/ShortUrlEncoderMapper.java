package com.example.urlmanagement.mapper;

import com.example.urlmanagement.domain.EncodeType;
import com.example.urlmanagement.encoder.Base58Encoder;
import com.example.urlmanagement.encoder.Base62Encoder;
import com.example.urlmanagement.encoder.ShortUrlEncoder;

public class ShortUrlEncoderMapper {

    public static ShortUrlEncoder createShortUrlEncoder(EncodeType encodeType) {

        ShortUrlEncoder shortUrlEncoder = null;

        switch (encodeType) {

            case BASE58 -> shortUrlEncoder = new Base58Encoder();

            case BASE62 -> shortUrlEncoder = new Base62Encoder();
        }

        return shortUrlEncoder;
    }
}
