package com.prgrms.shorturl.domain;

import com.prgrms.shorturl.utils.Base62EncodingFactory;
import com.prgrms.shorturl.utils.EncodingFactory;
import com.prgrms.shorturl.utils.HashAlgorithm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Url {

    @Id @GeneratedValue
    @Column(name = "short_url_id")
    private Long id;

    private String hash;

    @Column(nullable = false)
    private String originalUrl;

    private String shortUrl;

    private int count;

    @Transient
    private HashAlgorithm hashAlgorithm;

    public Url(String originalUrl, HashAlgorithm hashAlgorithm) {
        this.originalUrl = originalUrl;
        this.hashAlgorithm = hashAlgorithm;
        this.count = 1;
    }

    public void encode() {
        EncodingFactory encodingFactory = new Base62EncodingFactory(hash);
        this.shortUrl = encodingFactory.encode();
    }

    public void countRequire() {
        count++;
    }

    public void hashing() {
        this.hash = hashAlgorithm.hashing(this);
    }

    public void changeHash(String hash) {
        this.hash = hash;
    }
}
