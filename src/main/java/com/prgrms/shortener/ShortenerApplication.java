package com.prgrms.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShortenerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShortenerApplication.class, args);
  }
}
