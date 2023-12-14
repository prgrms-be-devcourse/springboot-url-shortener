package marco.urlshortener.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Entity
@Table(name = "URL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {
    private static final String BLANK_URL_MESSAGE = "url은 공백일 수 없습니다.";
    private static final String OVER_255_URL_MESSAGE = "url은 255자 이내여야 합니다.";
    private static final String INVALID_URL_FORMAT_MESSAGE = "유효하지 않은 url 형식입니다.";
    private static final Pattern PATTERN = Pattern.compile(
            "^(https?)://([^:/\\s]+)(:([^/]*))?((/[^\\s/]+)*)?/?([^#\\s?]*)(\\?([^#\\s]*))?(#(\\w*))?$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "long_url", nullable = false)
    private String longUrl;

    public Url(String longUrl) {
        validateLongUrl(longUrl);

        this.longUrl = longUrl;
    }

    private void validateLongUrl(String longUrl) {
        if (StringUtils.isBlank(longUrl)) {
            throw new IllegalArgumentException(BLANK_URL_MESSAGE);
        }

        if (longUrl.length() > 255) {
            throw new IllegalArgumentException(OVER_255_URL_MESSAGE);
        }

        if (!PATTERN.matcher(longUrl).matches()) {
            throw new IllegalArgumentException(INVALID_URL_FORMAT_MESSAGE);
        }
    }
}
