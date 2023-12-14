package com.example.urlmanagement.application;

import com.example.urlmanagement.domain.EncodeType;
import com.example.urlmanagement.domain.Url;
import com.example.urlmanagement.dto.request.CreateShortUrlRequest;
import com.example.urlmanagement.encoder.ShortUrlEncoder;
import com.example.urlmanagement.exception.UrlNotFoundException;
import com.example.urlmanagement.mapper.ShortUrlEncoderMapper;
import com.example.urlmanagement.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public String createShortUrl(CreateShortUrlRequest createShortUrlRequest) {

        Url url = urlRepository.save(createShortUrlRequest.toUrl());

        EncodeType encodeType = EncodeType.getEncodeTypeByName(createShortUrlRequest.getEncodeType());
        ShortUrlEncoder shortUrlEncoder = ShortUrlEncoderMapper.createShortUrlEncoder(encodeType);

        String shortUrl = shortUrlEncoder.createShortUrl(url.getId());
        url.updateShortUrl(shortUrl);

        return shortUrl;
    }

    @Transactional
    public String getOriginalUrl(String shortUrl) {

        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException(shortUrl));
        url.increaseRequestCount();

        return url.getOriginalUrl();
    }
}
