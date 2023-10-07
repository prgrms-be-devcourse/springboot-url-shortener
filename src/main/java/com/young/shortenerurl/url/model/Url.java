package com.young.shortenerurl.url.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Table(name = "urls")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String originUrl;

    @Embedded
    private EncodedUrl encodedUrl;

    @Column(nullable = false)
    private Long visitCount;

    public Url(String originUrl, EncodedUrl encodedUrl) {
        Assert.notNull(originUrl, "originUrl은 null값이 들어올 수 없습니다.");
        Assert.notNull(encodedUrl, "encodedUrl은 null값이 들어올 수 없습니다.");

        this.originUrl = originUrl;
        this.encodedUrl = encodedUrl;
        this.visitCount = 0L;
    }

    public String getEncodedUrl() {
        return encodedUrl.getEncodedUrl();
    }

    public void increaseVisitCount(){
        visitCount++;
    }

}
