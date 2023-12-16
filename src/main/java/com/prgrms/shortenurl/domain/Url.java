package com.prgrms.shortenurl.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shortenUrl")
    @SequenceGenerator(name = "shortenUrl", sequenceName = "shortenUrl", allocationSize = 1, initialValue = 100)
    private Long id;

    @Column(name = "shorten_key")
    private String shortenKey;

    @NonNull
    @Column(name = "orgin_url")
    private String originUrl;

    @Column(name = "shorten_url", unique = true)
    private String shortenUrl;

    @Column(name = "count")
    private int count;

    @Builder
    public Url(@NonNull String originUrl) {
        this.originUrl = originUrl;
    }

    public void updateShortenUrl(String shortenKey) {
        this.shortenKey = shortenKey;
        this.shortenUrl = "http://localhost:8090/"+shortenKey;
    }

    public void updateCount() {
        this.count += 1;
    }
}
