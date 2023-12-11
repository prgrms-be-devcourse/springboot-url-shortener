package kdt.shorturl.url.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.NoSuchElementException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(schema = "url")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm")
    private Algorithm algorithm;

    @Column(name = "view_count", nullable = false)
    @ColumnDefault("0")
    private Long viewCount;

    @Builder
    public Url(Long id, String originUrl, Algorithm algorithm) {
        this.id = id;
        this.originUrl = originUrl;
        this.algorithm = algorithm;
    }

    public void convertToShortUrl() {
        if (id == null) {
            throw new NoSuchElementException("존재하지 않는 url입니다.");
        }
        this.shortUrl = algorithm.getShortUrl(id.intValue());
    }

    public void increaseCount() {
        viewCount++;
    }
}
