package com.youngurl.shortenerurl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

    @Enumerated
    @Column(nullable = false)
    private EncodingType encodingType;

    @Transient
    private Encoder encoder;

    public Url(String originUrl, EncodingType encodingType) {
        Assert.notNull(originUrl, "originUrl은 null값이 들어올 수 없습니다.");
        Assert.notNull(encodingType, "encodingType은 null값이 들어올 수 없습니다.");

        this.originUrl = originUrl;
        this.encodingType = encodingType;
        this.encoder = encodingType.getEncoder();
    }

    public String encode(){
        String encoded = encoder.encode(this.id);
        this.encodedUrl = new EncodedUrl(encoded);

        return encoded;
    }

    public String getEncodedUrl() {
        return encodedUrl.getEncodedUrl();
    }

}
