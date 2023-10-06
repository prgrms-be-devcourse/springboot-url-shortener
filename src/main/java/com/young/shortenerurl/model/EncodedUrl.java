package com.young.shortenerurl.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Embeddable
@NoArgsConstructor
public class EncodedUrl {
    private static final int MAX_URL_LENGTH = 8;

    @Column
    private String encodedUrl;

    public EncodedUrl(String encodedUrl) {
        Assert.notNull(encodedUrl, "변환된 url은 null이 될 수 없습니다.");

        if (encodedUrl.length() > MAX_URL_LENGTH){
            throw new IllegalArgumentException("변환된 url의 길이는 8이상 될 수 없습니다.");
        }

        this.encodedUrl = encodedUrl;
    }

}
