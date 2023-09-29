package com.prgrms.urlshortener.config;

import com.prgrms.urlshortener.domain.Urls;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@EnableScheduling
@EnableCaching
@Configuration
public class AppConfig {

    @Bean
    public Map<String, Urls> urlStore() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public String BASE62(@Value("${base62}") String base62) {
        return base62;
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Collections.singletonList(new ConcurrentMapCache("urls")));
        return cacheManager;
    }

    /**
     * 캐시 비우기 redis 외부 캐시 쓰면 스케쥴링 할 필요 없이 ttl 지원해줌
     * */
//    @CacheEvict(value = "urls", allEntries = true)
//    @Scheduled(fixedRateString = "${ttl}")
//    public void clearUrlCache() {
//    }
}