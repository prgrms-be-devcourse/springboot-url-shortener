package com.programmers.springbooturlshortener.domain.url;

import com.programmers.springbooturlshortener.domain.algorithm.Base62Algorithm;
import com.programmers.springbooturlshortener.domain.url.dto.UrlCreateDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Algorithm base62Algorithm;

    @Transactional
    public UrlResponseDto createShortUrl(UrlCreateDto urlCreateDto) {

        Optional<Url> optionalUrl = urlRepository.findByOriginUrl(urlCreateDto.originUrl());

        if (optionalUrl.isPresent()) {
            Url url = optionalUrl.get();
            optionalUrl.get().increaseRequestCount();

            return new UrlResponseDto(url.getOriginUrl(), url.getShortUrl(), url.getRequestCount());
        }

        Url url = urlCreateDto.toEntity();
        Url savedUrl = urlRepository.save(url);
        String shortUrl = base62Algorithm.encode(savedUrl.getId());
        savedUrl.setShortUrl(shortUrl);
        return new UrlResponseDto(savedUrl.getOriginUrl(), shortUrl, 1L);
    }
}

