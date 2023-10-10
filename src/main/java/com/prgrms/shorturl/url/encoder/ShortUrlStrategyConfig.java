package com.prgrms.shorturl.url.encoder;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ShortUrlStrategyConfig {

    private final Map<String, ShortUrlStrategy> strategies = new HashMap<>();

    public ShortUrlStrategyConfig(Base62Encoder base62Encoder, RandomEncoder randomEncoder) {
        strategies.put("RANDOM", randomEncoder);
        strategies.put("BASE62", base62Encoder);
    }

    public ShortUrlStrategy find(String strategy) {
        return strategies.get(strategy);
    }

}
