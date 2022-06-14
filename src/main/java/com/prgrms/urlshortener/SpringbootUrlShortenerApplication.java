package com.prgrms.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringbootUrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootUrlShortenerApplication.class, args);
    }

}
