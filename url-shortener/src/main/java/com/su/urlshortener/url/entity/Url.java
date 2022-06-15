package com.su.urlshortener.url.entity;

import com.su.urlshortener.common.BaseEntity;
import com.su.urlshortener.url.service.shortener.ShorteningAlgorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(callSuper = true)
public class Url extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String originUrl;

    @Column(unique = true)
    private String shotToken;

    @Enumerated(EnumType.STRING)
    private ShorteningAlgorithm algorithm;

    private long visitNum = 0;

    private Url(String originUrl, String shotToken, ShorteningAlgorithm algorithm) {
        checkArgument(originUrl.length() != 0, "Invalid Url Value : 올바른 url을 입력해주세요 (길이 != 0)");
        checkArgument(shotToken.length() == 8, "Invalid Token Value : 8글자 토큰을 발급받으세요.");
        this.originUrl = originUrl;
        this.shotToken = shotToken;
        this.algorithm = algorithm;
    }

    public static Url of(String originUrl, String shortUrl, ShorteningAlgorithm algorithm) {
        return new Url(originUrl, shortUrl, algorithm);
    }

    public long visitUrl() {
        return ++visitNum;
    }
}
