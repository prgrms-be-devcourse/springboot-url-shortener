package devcourse.springbooturlshortener.entity;

import devcourse.springbooturlshortener.entity.vo.OriginalUrl;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "urls")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    private static final Long DEFAULT_HIT = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "short_url", length = 8)
    private String shortUrl;

    @Embedded
    private OriginalUrl originalUrl;

    @Column(name = "hit", nullable = false)
    private Long hit;

    @Builder
    public Url(OriginalUrl originalUrl) {
        this.originalUrl = originalUrl;
        this.hit = DEFAULT_HIT;
    }

    public Url updateShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
        return this;
    }
}
