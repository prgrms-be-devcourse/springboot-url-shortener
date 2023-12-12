package kdt.shorturl.url.service;

import kdt.shorturl.url.domain.Url;
import kdt.shorturl.url.dto.CreateShortUrlRequest;
import kdt.shorturl.url.dto.CreateShortenUrlResponse;
import kdt.shorturl.url.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public CreateShortenUrlResponse findOrGenerateShortenUrl(CreateShortUrlRequest request) {
        return urlRepository.findByOriginUrlAndAlgorithm(request.originUrl(), request.algorithm())
                .map(foundUrl -> CreateShortenUrlResponse.from(foundUrl, false))
                .orElseGet(() -> generateShortenUrl(request));
    }

    @Transactional(readOnly = true)
    public String findOriginUrlByShortUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NoSuchElementException("해당 단축 URL을 찾을 수 없습니다."));
        return url.getOriginUrl();
    }

    private CreateShortenUrlResponse generateShortenUrl(CreateShortUrlRequest request) {
        Url savedUrl = urlRepository.save(request.toEntity());
        savedUrl.convertToShortUrl();
        return CreateShortenUrlResponse.from(savedUrl, true);
    }
}
