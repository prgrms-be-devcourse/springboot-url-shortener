package devcourse.springbooturlshortener.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OriginalUrl {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    @Column(name = "original_url", nullable = false, length = 2000)
    private String value;

    public OriginalUrl(String originalUrl) {
        this.value = originalUrl;

        this.trimUrl()
                .deleteHttpPrefix()
                .lowercaseUrl();
    }

    private OriginalUrl trimUrl() {
        this.value = this.value.trim();

        return this;
    }

    private OriginalUrl deleteHttpPrefix() {
        if (this.value.startsWith(HTTP_PREFIX)) {
            this.value = this.value.substring(HTTP_PREFIX.length());
        }
        if (this.value.startsWith(HTTPS_PREFIX)) {
            this.value = this.value.substring(HTTPS_PREFIX.length());
        }

        return this;
    }

    private OriginalUrl lowercaseUrl() {
        this.value = this.value.toLowerCase();

        return this;
    }
}
