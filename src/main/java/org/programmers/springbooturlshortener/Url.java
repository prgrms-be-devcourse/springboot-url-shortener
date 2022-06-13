package org.programmers.springbooturlshortener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    public Url(String original) {
        this.original = original;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String original;
    @Column(nullable = false)
    private long called = 0;

    public long addCalledTime() {
        return ++called;
    }
}