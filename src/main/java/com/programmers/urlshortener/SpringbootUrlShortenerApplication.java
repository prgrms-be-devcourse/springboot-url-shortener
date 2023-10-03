package com.programmers.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootUrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootUrlShortenerApplication.class, args);
    }
}
