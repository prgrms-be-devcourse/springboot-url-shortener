package com.blessing333.urlshortner.domain.model.url.shortener;

import com.blessing333.urlshortner.domain.model.url.key.Base62KeyGenerator;
import com.blessing333.urlshortner.domain.model.url.key.KeyGenerator;
import com.blessing333.urlshortner.domain.model.url.shortener.impl.BasicUrlShortener;
import com.blessing333.urlshortner.domain.model.url.shortener.impl.CustomDomainUrlShortener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlShortenerConfig {
    @Bean
    public UrlShortener customDomainUrlShortener() {
        return new CustomDomainUrlShortener(keyGenerator());
    }

    @Bean
    public UrlShortener basicUrlShortener() {
        return new BasicUrlShortener(keyGenerator());
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new Base62KeyGenerator();
    }
}
