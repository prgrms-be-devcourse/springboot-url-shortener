package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

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
        this.originUrl = originUrl;
        this.calledTimes = calledTimes;
    }
}
