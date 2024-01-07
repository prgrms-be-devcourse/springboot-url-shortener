package com.pgms.shorturlapi.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        return new ThreadPoolExecutor(1, 5, 3, TimeUnit.SECONDS, queue);
    }
}
