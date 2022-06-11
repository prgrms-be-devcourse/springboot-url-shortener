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
@NoArgsConstructor(access = PROTECTED)
public class ShortUrl extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "short_url_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "origin_url", nullable = false, updatable = false, length = 500)
    private String originUrl;

    @Column(name = "short_url", nullable = false, length = 100)
    private String shortUrl;

    @Column(name = "request_count", nullable = false)
    private Long requestCount;

    public ShortUrl(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.requestCount = 0L;
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
