package com.young.shortenerurl.url.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EncodedUrl {
    private static final int MAX_URL_LENGTH = 8;

    @Column(nullable = false, name = "encoded_url")
    private String encodedUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EncodingType encodingType;

    public EncodedUrl(String encodedUrl, EncodingType encodingType) {
        Assert.notNull(encodedUrl, "변환된 url은 null이 될 수 없습니다.");
        Assert.notNull(encodingType, "encodingType은 null이 될 수 없습니다.");

        if (encodedUrl.length() > MAX_URL_LENGTH) {
            throw new IllegalArgumentException("변환된 url의 길이는 8이상 될 수 없습니다.");
        }

        this.encodedUrl = encodedUrl;
        this.encodingType = encodingType;
    }

}
