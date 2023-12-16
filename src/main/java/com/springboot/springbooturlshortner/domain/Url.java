package com.springboot.springbooturlshortner.domain;

import com.springboot.springbooturlshortner.exception.UrlException;
import com.springboot.springbooturlshortner.exception.UrlExceptionCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Entity
@Table(name = "urls")
@NoArgsConstructor
@Getter
public class Url {

    private static final String ORIGIN_URL_PATTERN = "^(https?://).*";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indexGenerator")
    @SequenceGenerator(name = "indexGenerator", initialValue = 10000)
    private Long id;
    @Column(name = "originUrl")
    private String originUrl;
    public Url(String originUrl) {
            this.originUrl = originUrl;
    }
}
