package com.prgrms.shorturl.utils;

import com.prgrms.shorturl.domain.Url;
import lombok.Getter;

@Getter
public enum HashAlgorithm {
    SHA_256 {
        @Override
        public String hashing(Url url) {
            HashFactory hashFactory = new HashFactory(SHA_256);
            return hashFactory.generate(url.getOriginalUrl());
        }
    },
    SEQUENCE {
        @Override
        public String hashing(Url url) {
            return Long.toHexString(url.getId());
        }
    };

    public String hashing(Url url) {
        return "default";
    }
}
