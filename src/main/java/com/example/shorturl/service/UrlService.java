package com.example.shorturl.service;

import static com.example.shorturl.exception.ErrorCode.*;

import com.example.shorturl.domain.Algorithm;
import com.example.shorturl.dto.request.ShortUrlCreateRequest;
import com.example.shorturl.dto.response.OriginUrlResponse;
import com.example.shorturl.dto.response.ShortUrlResponse;
import com.example.shorturl.domain.Url;
import com.example.shorturl.exception.CustomException;
import com.example.shorturl.repository.UrlRepository;
import com.example.shorturl.util.encoder.Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UrlService {

    private final UrlRepository urlRepository;
    private final Encoder encoder;

    public ShortUrlResponse createOrGetShortUrl(ShortUrlCreateRequest request) {
        Url url = urlRepository.findUrlByOriginUrlAndAlgorithm(request.originUrl(), request.algorithm())
            .orElseGet(() -> saveNewShortUrl(request));
        return ShortUrlResponse.toDto(url);
    }

    public OriginUrlResponse getOriginUrl(String shortUrl) {
        Url url = urlRepository.findUrlByShortUrl(shortUrl)
            .orElseThrow(() -> new CustomException(URL_NOT_FOUND));
        url.increaseRequestCount();
        return OriginUrlResponse.toDto(url.getOriginUrl());
    }

    private Url saveNewShortUrl(ShortUrlCreateRequest request) {
        Url url = urlRepository.save(new Url(request.originUrl(), request.algorithm()));
        url.updateShortUrl(encodingUrl(url.getId(), url.getAlgorithm()));
        return url;
    }

    private String encodingUrl(Long urlSequence, Algorithm algorithm) {
        return encoder.encodeUrl(urlSequence, algorithm);
    }
}
