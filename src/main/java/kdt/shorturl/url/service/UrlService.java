package kdt.shorturl.url.service;

import kdt.shorturl.grobal.exception.ShortUrlException;
import kdt.shorturl.url.converter.Base62Converter;
import kdt.shorturl.url.converter.Sha256Converter;
import kdt.shorturl.url.converter.ShortUrlConverter;
import kdt.shorturl.url.domain.Algorithm;
import kdt.shorturl.url.domain.Url;
import kdt.shorturl.url.dto.CreateShortUrlRequest;
import kdt.shorturl.url.dto.CreateShortenUrlResponse;
import kdt.shorturl.url.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private ShortUrlConverter shortUrlConverter;

    @Transactional
    public CreateShortenUrlResponse findOrGenerateShortenUrl(CreateShortUrlRequest request) {
        String urlWithoutProtocol = request.removeProtocol(request.originUrl());
        return urlRepository.findByOriginUrlAndAlgorithm(urlWithoutProtocol, request.algorithm())
                .map(foundUrl -> CreateShortenUrlResponse.from(foundUrl, false))
                .orElseGet(() -> generateShortenUrl(request));
    }

    @Transactional
    public String findOriginUrlByShortUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ShortUrlException("해당 URL에 대한 단축 URL을 찾을 수 없습니다."));
        url.increaseCount();
        return url.getOriginUrl();
    }

    private CreateShortenUrlResponse generateShortenUrl(CreateShortUrlRequest request) {
        Url savedUrl = urlRepository.save(request.toEntity());
        shortUrlConverter = createConverter(request.algorithm());
        savedUrl.addShortUrl(shortUrlConverter.encoding(savedUrl.getId().intValue()));
        return CreateShortenUrlResponse.from(savedUrl, true);
    }

    private ShortUrlConverter createConverter(Algorithm algorithm) {
        return switch (algorithm) {
            case BASE62 -> new Base62Converter();
            case SHA256 -> new Sha256Converter(urlRepository);
        };
    }
}
