package org.programmers.springbooturlshortener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.springbooturlshortener.encoding.Encoding;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    public Url(String original, Encoding encoding) {
        this.original = original;
        this.encoding = encoding;
    }

    public Url(Long id, String original, Encoding encoding) {
        this.id = id;
        this.original = original;
        this.encoding = encoding;
    }

    @Id
    private Long id;
    private String original;
    @Column(nullable = false)
    private int called;
    private Encoding encoding;
}