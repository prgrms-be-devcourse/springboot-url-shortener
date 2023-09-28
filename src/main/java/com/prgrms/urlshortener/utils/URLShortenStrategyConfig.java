package com.prgrms.urlshortener.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class URLShortenStrategyConfig {

    @Bean
    public Map<String, URLShorteningStrategy> strategies(RandomStrategy randomStrategy,
                                                         HashStrategy hashStrategy) {
        Map<String, URLShorteningStrategy> strategies = new HashMap<>();
        strategies.put("random", randomStrategy);
        strategies.put("hash", hashStrategy);
        return strategies;
    }
}