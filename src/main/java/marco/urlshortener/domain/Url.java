package marco.urlshortener.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "URL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "long_url", nullable = false)
    private String longUrl;

    @Column(name = "short_url")
    private String shortUrl;

    public Url(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
