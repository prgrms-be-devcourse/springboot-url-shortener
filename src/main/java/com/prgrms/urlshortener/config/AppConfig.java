package com.prgrms.urlshortener.config;

import com.prgrms.urlshortener.domain.Urls;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AppConfig {

    @Bean
    public Map<String, Urls> urlStore() {
        return new ConcurrentHashMap<>();
    }
}