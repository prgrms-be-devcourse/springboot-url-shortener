package kdt.shorturl.url.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(schema = "url")
public class Url {

    public static final long DEFAULT_VIEW = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name = "short_url", unique = true)
    private String shortUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm")
    private Algorithm algorithm;

    @Column(name = "view_count")
    private long viewCount = DEFAULT_VIEW;

    @Builder
    public Url(Long id, String originUrl, Algorithm algorithm) {
        this.id = id;
        this.originUrl = originUrl;
        this.algorithm = algorithm;
    }

    public void addShortUrl(String shortUrl) {
        if (id == null) {
            throw new NullPointerException("ID가 존재하지 않습니다.");
        }
        this.shortUrl = shortUrl;
    }

    public void increaseCount() {
        viewCount++;
    }
}
