package com.prgrms.shortenurl.url.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shortenUrl")
    @SequenceGenerator(name = "shortenUrl", sequenceName = "shortenUrl", allocationSize = 1)
    private Long id;

    @NonNull
    @Column(name = "shorten_key")
    private String shortenKey;

    @NonNull
    @Column(name = "orgin_url")
    private String originUrl;

    @NonNull
    @Column(name = "shorten_url")
    private String shortenUrl;

    @Builder
    public Url(@NonNull String shortenKey, @NonNull String originUrl, @NonNull String shortenUrl) {
        this.shortenKey = shortenKey;
        this.originUrl = originUrl;
        this.shortenUrl = shortenUrl;
    }
}
