package com.pgms.shorturlcoredomain.url;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String originalUrl;

    private String shortUrl;

    public void updateUrlShortUrl(String shortUrl){
        this.shortUrl = shortUrl;
    }
}
