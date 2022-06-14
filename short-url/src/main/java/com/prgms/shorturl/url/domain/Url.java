package com.prgms.shorturl.url.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "url")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Url {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "long_url", nullable = false)
    private String longUrl;

    @Column(name = "call_count", nullable = false)
    private long callCount = 0;

    public Url(String longUrl) {
        this.longUrl = longUrl;
    }

    public Url(Long id, String longUrl) {
        this.id = id;
        this.longUrl = longUrl;
    }

    public long addCallCount() {
        return callCount++;
    }
}
