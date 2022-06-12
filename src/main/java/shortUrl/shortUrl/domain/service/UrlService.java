package shortUrl.shortUrl.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import shortUrl.shortUrl.domain.entity.Url;
import shortUrl.shortUrl.domain.repository.UrlRepository;
import shortUrl.shortUrl.domain.value.Algorithm;
import shortUrl.shortUrl.domain.dto.CreateShortUrlDto;
import shortUrl.shortUrl.domain.dto.ShortUrlDto;
import shortUrl.shortUrl.exception.AlreadyExistException;
import shortUrl.shortUrl.exception.NotExistException;
import shortUrl.shortUrl.exception.WrongUrlException;

import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public ShortUrlDto createShortUrl(CreateShortUrlDto createShortUrlDto) {
        String originalUrl = createShortUrlDto.getOriginalUrl();
        Algorithm algorithm = createShortUrlDto.getAlgorithm();
        ShortUrlDto shortUrlDto = new ShortUrlDto();

        if (!validateUrl(originalUrl)) {
            throw new WrongUrlException("잘못된 url 입력입니다.");
        }

        // TODO: 2022/06/11 이미 존재하는 url이 있을경우 - 코드 개선 방향이 있는가?
        Optional<Url> existUrl = urlRepository.findByOriginalUrlAndAlgorithm(originalUrl, algorithm);
        if (existUrl.isPresent()) {
            log.info("algorithm[{}] 을 사용한 동일한 originalUrl[{}] 이 존재합니다.", algorithm, originalUrl);
            shortUrlDto.setShortUrl(existUrl.get().getShortUrl());
            return shortUrlDto;
        }

        Url url = new Url(originalUrl, algorithm);
        Url savedUrl = urlRepository.save(url);

        Long id = savedUrl.getId();
        String shortUrl = algorithm.encoding(id);

        // TODO: 2022/06/12 존재하면 그 후 처리는? error log를 찍고 예외 처리가 아닌 char length가 +1인 url을 반환 후 보수?
        if (urlRepository.existsByShortUrl(shortUrl)) {
            log.error("중복되는 shortUrl 발생!!! originalUrl => {}, algorithm => {}, shortUrl => {}",
                    originalUrl, algorithm, shortUrl);
            throw new AlreadyExistException("이미 존재하는 short url 입니다.");
        }
        savedUrl.saveShortUrl(shortUrl);
        shortUrlDto.setShortUrl(shortUrl);
        return shortUrlDto;
    }

    public String findOriginUrlByShortUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NotExistException("존재하지 않습니다."));
        url.increaseHits();

        return url.getOriginalUrl();
    }

    @Transactional(readOnly = true)
    public ShortUrlDto getUrlInfo(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                        .orElseThrow(() -> new NotExistException("존재하지 않습니다."));
        return ShortUrlDto.builder()
                .originalUrl(url.getOriginalUrl())
                .shortUrl(shortUrl)
                .hits(url.getHits())
                .algorithm(url.getAlgorithm())
                .build();
    }

    /**
     * url 요청 후 2xx, 3xx 응답일 경우 true, 아닐경우 false
     */
    private boolean validateUrl(String originalUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpStatus statusCode
                = restTemplate.exchange(originalUrl, HttpMethod.HEAD, null, HttpResponse.class)
                .getStatusCode();
        System.out.println("statusCode = " + statusCode);
        return statusCode.is2xxSuccessful() || statusCode.is3xxRedirection();
    }
}
