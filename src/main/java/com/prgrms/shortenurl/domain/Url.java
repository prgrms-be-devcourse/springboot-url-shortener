package com.prgrms.shortenurl.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updated_at;

    @Builder
    public Url(@NonNull String originUrl) {
        if (isValidUrl(originUrl)) {
            this.originUrl = originUrl;
        } else {
            throw new IllegalArgumentException("Invalid URL: " + originUrl);
        }
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public void updateShortenUrl(String shortenKey) {
        this.shortenKey = shortenKey;
        this.shortenUrl = "http://localhost:8090/"+shortenKey;
    }

    public void updateCount() {
        this.count += 1;
    }
}
