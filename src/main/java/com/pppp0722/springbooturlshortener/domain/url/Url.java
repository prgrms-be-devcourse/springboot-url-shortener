package com.pppp0722.springbooturlshortener.domain.url;

import com.pppp0722.springbooturlshortener.exception.IllegalUrlArgumentException;
import com.pppp0722.springbooturlshortener.util.Regex;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String originalUrl;

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void changeOriginalUrl(String originalUrl) {
        if (!Pattern.matches(Regex.URL, originalUrl)) {
            throw new IllegalUrlArgumentException("올바르지 않은 URL 형식입니다!");
        }
        this.originalUrl = originalUrl;
    }
}
