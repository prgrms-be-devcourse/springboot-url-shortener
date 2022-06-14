package com.blessing333.urlshortner.domain.model.url;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Url {
    @Id
    private String id;
    private String originalUrl;
    private String shortUrl;
    private Long viewCount;
    private LocalDateTime createdDate;

    public static Url createNewUrl(String id, String originalUrl, String shortUrl) {
        Url url = new Url();
        url.id = id;
        url.originalUrl = originalUrl;
        url.shortUrl = shortUrl;
        url.viewCount = 0L;
        url.createdDate = LocalDateTime.now();
        return url;
    }

    public void increaseViewCount() {
        viewCount++;
    }
}
