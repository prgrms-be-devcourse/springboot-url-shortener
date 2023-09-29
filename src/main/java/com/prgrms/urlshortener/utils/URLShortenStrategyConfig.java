package com.prgrms.urlshortener.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class URLShortenStrategyConfig {

    public enum StrategyType {
        RANDOM("random"), HASH("hash");

        private final String type;

        StrategyType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    @Bean
    public Map<String, URLShorteningStrategy> strategies(RandomStrategy randomStrategy,
                                                         HashStrategy hashStrategy) {
        Map<String, URLShorteningStrategy> strategies = new HashMap<>();
        strategies.put(StrategyType.RANDOM.getType(), randomStrategy);
        strategies.put(StrategyType.HASH.getType(), hashStrategy);
        return strategies;
    }
}