package com.example.urlshortenert2.ShorteningKeyMaker;

import io.seruco.encoding.base62.Base62;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class Base62LibraryKeyMaker implements ShorteningKeyMaker {

    @Override
    public String makeShorteningKey(Long id) {
        //TODO: 여기 로직을 라이브러리 써서 하는게 생각해봐야 할 듯 => 키가 너무 짧다.
        Base62 base62 = Base62.createInstance();
        byte[] encoded = base62.encode(id.toString().getBytes());
        return new String(encoded);
    }
}
