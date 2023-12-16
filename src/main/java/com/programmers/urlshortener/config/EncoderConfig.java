package com.programmers.urlshortener.config;

import com.programmers.urlshortener.util.encoder.Base62UrlEncoder;
import com.programmers.urlshortener.util.encoder.Sha256UrlEncoder;
import com.programmers.urlshortener.util.encoder.UrlEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EncoderConfig {

    @Value("${url.shortening.algorithm:default}")
    private String algorithmType;

    @Bean
    public UrlEncoder encoder() {
        switch (algorithmType) {
            case "base62":
                return new Base62UrlEncoder();
            case "sha256":
                return new Sha256UrlEncoder();
            default:
                log.warn("URL 단축 알고리즘이 유효하지 않습니다. 기본값으로 Base62 인코더를 사용합니다.");
                return new Base62UrlEncoder();
        }
    }
}
