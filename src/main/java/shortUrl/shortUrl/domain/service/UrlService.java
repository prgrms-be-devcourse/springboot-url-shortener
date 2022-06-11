package shortUrl.shortUrl.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shortUrl.shortUrl.domain.entity.Url;
import shortUrl.shortUrl.domain.repository.UrlRepository;
import shortUrl.shortUrl.domain.value.Algorithm;
import shortUrl.shortUrl.domain.dto.CreateShortUrlDto;
import shortUrl.shortUrl.domain.dto.ShortUrlDto;
import shortUrl.shortUrl.exception.AlreadyExistException;
import shortUrl.shortUrl.exception.NotExistException;

import java.util.Optional;


@Service
@Slf4j
@Transactional
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String createShortUrl(CreateShortUrlDto createShortUrlDto) {
        String originalUrl = createShortUrlDto.getOriginalUrl();
        Algorithm algorithm = createShortUrlDto.getAlgorithm();

        // TODO: 2022/06/11 이미 존재하는 url이 있을경우 - 코드 개선 방향이 있는가?
        Optional<Url> existUrl = urlRepository.findByOriginalUrlAndAlgorithm(originalUrl, algorithm);
        if (existUrl.isPresent()) {
            log.info("algorithm[{}] 을 사용한 동일한 originalUrl[{}] 이 존재합니다.", algorithm, originalUrl);
            return existUrl.get().getShortUrl();
        }

        Url url = new Url(originalUrl, algorithm);
        Url savedUrl = urlRepository.save(url);

        Long id = savedUrl.getId();
        String shortUrl = algorithm.encoding(id);

        // TODO: 2022/06/12 존재하면 그 후 처리는?
        if (urlRepository.existsByShortUrl(shortUrl)) {
            throw new AlreadyExistException("이미 존재하는 short url 입니다.");
        }
        savedUrl.saveShortUrl(shortUrl);
        return shortUrl;
    }

    public String findOriginUrl(ShortUrlDto shortUrlDto) {
        String shortUrl = shortUrlDto.getShortUrl();
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NotExistException("존재하지 않습니다"));
        url.hitCount();
        return url.getOriginalUrl();
    }

    @Transactional(readOnly = true)
    public ShortUrlDto getUrlInfo(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                        .orElseThrow(() -> new NotExistException("존재하지 않습니다"));
        return ShortUrlDto.builder()
                .originalUrl(url.getOriginalUrl())
                .hits(url.getHits())
                .algorithm(url.getAlgorithm())
                .build();
    }
}
