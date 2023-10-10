package com.prgrms.shorturl.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheEvictScheduler {

    private final CacheManager cacheManager;

    @Autowired
    public CacheEvictScheduler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = 3600000)
    public void evictUrlsCache() {
        log.info("캐시는 1시간 뒤에 지워집니다.");
        cacheManager.getCache("urls").clear();
    }

}
