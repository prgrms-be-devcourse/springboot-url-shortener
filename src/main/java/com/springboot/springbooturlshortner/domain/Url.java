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
@Slf4j
public class Url {

    private static final String ORIGIN_URL_PATTERN = "^(https?://).*";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indexGenerator")
    @SequenceGenerator(name = "indexGenerator", initialValue = 10000)
    private Long id;
    @Column(name = "originUrl")
    private String originUrl;
    @Column(name = "requestCnt")
    private long requestCnt = 0;

    public Url(String originUrl) {
        if (checkOriginUrl(originUrl)) {
            this.originUrl = originUrl;
        } else {
            throw new UrlException(UrlExceptionCode.INVALID_ORIGIN_URL);
        }
    }

    public void increaseRequestCntOne() {
        this.requestCnt++;
    }

    private boolean checkOriginUrl(String originUrl) {
        return Pattern.matches(ORIGIN_URL_PATTERN, originUrl);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
