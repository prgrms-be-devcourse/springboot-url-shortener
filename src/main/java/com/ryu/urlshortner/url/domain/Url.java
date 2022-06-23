package com.ryu.urlshortner.url.domain;

import com.ryu.urlshortner.common.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "url")
public class Url extends BaseEntity {

    @Id
    @SequenceGenerator(name = "seq_generator", sequenceName = "url_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @Column(name = "seq")
    private long seq;

    @Column(name = "origin_url")
    private String originUrl;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "hits")
    private long hits;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    protected Url() {
    }

    private Url(Builder builder) {
        this.seq = builder.seq;
        this.originUrl = builder.originUrl;
        this.shortUrl = builder.shortUrl;
        this.hits = builder.hits;
        this.expiredAt = builder.expiredAt;
    }

    public long getSeq() {
        return seq;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void increaseHits() {
        this.hits += 1;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long seq;
        private String originUrl;
        private String shortUrl;
        private long hits;
        private LocalDateTime expiredAt;

        public Builder seq(long seq) {
            this.seq = seq;
            return this;
        }

        public Builder originUrl(String originUrl) {
            this.originUrl = originUrl;
            return this;
        }

        public Builder shortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
            return this;
        }

        public Builder hits(long hits) {
            this.hits = hits;
            return this;
        }

        public Builder expiredAt(LocalDateTime expiredAt) {
            this.expiredAt = expiredAt;
            return this;
        }

        public Url build() {
            return new Url(this);
        }
    }
}
