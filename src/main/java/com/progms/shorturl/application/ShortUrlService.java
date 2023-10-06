package com.progms.shorturl.application;

import com.progms.shorturl.dto.UrlRequest;
import com.progms.shorturl.dto.UrlResponse;
import com.progms.shorturl.entity.ShortUrl;
import com.progms.shorturl.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final UrlGenerationService urlGenerationService;
    private final ShortUrlRepository shortUrlRepository;

    @Transactional
    public UrlResponse generateUrl(UrlRequest urlRequest) {
        Optional<ShortUrl> optionalShortUrl = shortUrlRepository.findByOriginUrl(urlRequest.url());

        if (optionalShortUrl.isPresent()) {
            ShortUrl shortUrl = optionalShortUrl.get();
            shortUrl.updateView();
            return UrlResponse.from(shortUrl);
        }

        ShortUrl shortUrl =urlRequest.toEntity();
        shortUrlRepository.save(shortUrl);

        String encodeUrl = urlGenerationService.parseBase62(shortUrl.getShortId());
        shortUrl.updateShortUrl(encodeUrl);

        return UrlResponse.from(shortUrl);
    }

    public String regenerateUrl(String shortUrl) {
        ShortUrl urlData = shortUrlRepository.findByShortUrl(shortUrl)
                .orElseThrow(()->new IllegalArgumentException("Error"));

        urlData.updateView();
        return urlData.getOriginUrl();
    }
}
