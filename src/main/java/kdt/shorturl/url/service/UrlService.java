package kdt.shorturl.url.service;

import kdt.shorturl.url.domain.Url;
import kdt.shorturl.url.dto.CreateShortUrlRequest;
import kdt.shorturl.url.dto.CreateShortenUrlResponse;
import kdt.shorturl.url.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public CreateShortenUrlResponse findOrGenerateShortenUrl(CreateShortUrlRequest request) {
        // 이미 변환된 적 있는 url인 경우
        Optional<Url> searchUrl = urlRepository.findByOriginUrlAndAlgorithm(request.originUrl(), request.algorithm());
        if (searchUrl.isPresent()) {
            Url foundUrl = searchUrl.get();
            return CreateShortenUrlResponse.from(foundUrl, false);
        }

        return generateShortenUrl(request);
    }

    private CreateShortenUrlResponse generateShortenUrl(CreateShortUrlRequest request) {
        Url savedUrl = urlRepository.save(request.toEntity());
        savedUrl.convertToShortUrl();
        return CreateShortenUrlResponse.from(savedUrl, true);
    }
}
