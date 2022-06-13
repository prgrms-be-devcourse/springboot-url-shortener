package shortUrl.shortUrl.domain.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import shortUrl.shortUrl.domain.value.Algorithm;
import shortUrl.shortUrl.exception.WrongUrlException;

import javax.persistence.*;
import java.net.http.HttpResponse;

@Entity
@Table(name = "url")
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String originalUrl;

    private String shortUrl;

    private Long hits;

    @Enumerated(EnumType.STRING)
    private Algorithm algorithm;

    public Url(String originalUrl, Algorithm algorithm) {
        if (!validateUrl(originalUrl)) {
            throw new WrongUrlException("잘못된 url 입력입니다.");
        }
        this.originalUrl = originalUrl;
        this.algorithm = algorithm;
        this.hits = 0L;
    }

    public void increaseHits() {
        this.hits += 1;
        log.info("조회 수 1 증가.");
    }

    public void saveShortUrl(String shortUrl) {
        if (this.shortUrl != null) {
            log.error("shortUrl 변경 감지. originalUrl => {}, algorithm => {}", this.originalUrl, this.algorithm);
            throw new RuntimeException("변경할 수 없습니다.");
        }
        this.shortUrl = shortUrl;
        log.info("shortUrl => {} 저장.", shortUrl);
    }

    /**
     * url 요청 후 2xx, 3xx 응답일 경우 true, 아닐경우 false
     */
    private boolean validateUrl(String originalUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpStatus statusCode
                = restTemplate.exchange(originalUrl, HttpMethod.HEAD, null, HttpResponse.class)
                .getStatusCode();
        log.info("statusCode => {}", statusCode);
        return statusCode.is2xxSuccessful() || statusCode.is3xxRedirection();
    }
}
