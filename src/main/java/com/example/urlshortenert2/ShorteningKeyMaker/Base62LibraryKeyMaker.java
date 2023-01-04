package com.example.urlshortenert2.ShorteningKeyMaker;

import io.seruco.encoding.base62.Base62;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Base62LibraryKeyMaker implements ShorteningKeyMaker{
    @Override
    public String makeShorteningKey(Long id) {
        Base62 base62 = Base62.createInstance();
        byte[] encoded = base62.encode(id.toString().getBytes());
        return new String(encoded);
    }
}
