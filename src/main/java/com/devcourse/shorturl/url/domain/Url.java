package com.devcourse.shorturl.url.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionId;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long id;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "request_hits")
    private int hits;

    public Url(String longUrl, String shortUrl, int hits) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.hits = hits;
    }

    public void addHits(){
        this.hits += 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return hits == url.hits && Objects.equals(id, url.id) && Objects.equals(longUrl, url.longUrl) && Objects.equals(shortUrl, url.shortUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, longUrl, shortUrl, hits);
    }
}
