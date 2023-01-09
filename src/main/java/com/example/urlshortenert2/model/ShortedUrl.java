package com.example.urlshortenert2.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String shorteningKey;
    @Column(nullable = false, unique = true)
    private String originUrl;

    public ShortedUrl(String shorteningKey, String originUrl) {
        this.shorteningKey = shorteningKey;
        this.originUrl = originUrl;
    }

    public ShortedUrl(String originUrl) {
        this(null, originUrl);
    }

    public void setShorteningKey(String shorteningKey) {
        this.shorteningKey = shorteningKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortedUrl that = (ShortedUrl) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
