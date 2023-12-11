package com.pgms.shorturlapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.pgms")
public class ShortUrlApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlApiApplication.class, args);
    }

}
