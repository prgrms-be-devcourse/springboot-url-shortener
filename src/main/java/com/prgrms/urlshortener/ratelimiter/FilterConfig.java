package com.prgrms.urlshortener.ratelimiter;

import io.github.bucket4j.Bucket;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<IPBasedRateLimitingFilter> loggingFilter(){
        FilterRegistrationBean<IPBasedRateLimitingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new IPBasedRateLimitingFilter(rateLimitingBuckets()));

        return registrationBean;    
    }

    @Bean
    public Map<String, Bucket> rateLimitingBuckets() {
        return new ConcurrentHashMap<>();
    }
}