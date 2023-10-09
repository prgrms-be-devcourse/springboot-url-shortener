package com.tangerine.urlshortener.url.model.vo;

import com.tangerine.urlshortener.url.algorithm.Base62BaseAlgorithm;
import com.tangerine.urlshortener.url.algorithm.Base64BaseAlgorithm;
import com.tangerine.urlshortener.url.algorithm.BaseAlgorithm;

public enum Algorithm {
    BASE62(new Base62BaseAlgorithm()),
    BASE64(new Base64BaseAlgorithm());

    private final BaseAlgorithm baseAlgorithm;

    Algorithm(BaseAlgorithm baseAlgorithm) {
        this.baseAlgorithm = baseAlgorithm;
    }

    public String encode(Long mappingId) {
        return baseAlgorithm.encode(mappingId);
    }
}
