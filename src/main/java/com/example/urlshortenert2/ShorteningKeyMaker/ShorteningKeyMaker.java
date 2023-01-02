package com.example.urlshortenert2.ShorteningKeyMaker;

import io.seruco.encoding.base62.Base62;
import org.springframework.stereotype.Component;

public interface ShorteningKeyMaker {
    String makeShorteningKey(Long id);
}
