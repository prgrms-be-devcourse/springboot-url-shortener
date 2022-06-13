package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "URL_SEQ_GENERATOR")
    @SequenceGenerator(
            name = "URL_SEQ_GENERATOR",
            initialValue = 1000000,
            allocationSize = 1)
    private Integer id;

    @Lob
    private String originUrl;

    private int calledTimes;

    public Url(String originUrl, int calledTimes) {
        validateUrl(originUrl);
        this.originUrl = originUrl;
        this.calledTimes = calledTimes;
    }

    private void validateUrl(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void increaseCalledTimes() {
        this.calledTimes++;
    }
}
