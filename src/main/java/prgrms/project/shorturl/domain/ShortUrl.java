package prgrms.project.shorturl.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Entity
@Getter
@SequenceGenerator(
    name = "SHORT_URL_SEQ_GEN",
    sequenceName = "SHORT_URL_SEQ",
    initialValue = 10000000
)
@NoArgsConstructor(access = PROTECTED)
public class ShortUrl extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SHORT_URL_SEQ_GEN")
    @Column(name = "short_url_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "origin_url", nullable = false, updatable = false, length = 500)
    private String originUrl;

    @Column(name = "short_url", length = 100)
    private String shortUrl;

    @Column(name = "request_count", nullable = false)
    private Long requestCount;

    public ShortUrl(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.requestCount = 0L;
    }

    public void assignShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public void increaseRequestCount() {
        this.requestCount++;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("originUrl", originUrl)
            .append("shortUrl", shortUrl)
            .append("requestCount", requestCount)
            .toString();
    }
}
