package com.programmers.springbooturlshortener.domain.application;

import com.programmers.springbooturlshortener.domain.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.domain.dto.UrlServiceResponseDto;
import com.programmers.springbooturlshortener.domain.entity.Url;
import com.programmers.springbooturlshortener.domain.exception.NoSuchUrlFoundException;
import com.programmers.springbooturlshortener.domain.infrastructure.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlServiceResponseDto createShortUrl(UrlServiceRequestDto requestDto) {
        String shortUrl = requestDto.getEncodeType().encode(requestDto.getLongUrl().getValue());
        Url url = urlRepository.findById(shortUrl)
                .orElseGet(() -> urlRepository.save(Url.builder()
                        .longUrl(requestDto.getLongUrl().getValue())
                        .shortUrl(shortUrl)
                        .encodeType(requestDto.getEncodeType())
                        .build())
                );
        return UrlServiceResponseDto.of(url);
    }

    public UrlServiceResponseDto findLongUrl(UrlServiceRequestDto requestDto) {
        Url url = urlRepository.findById(requestDto.getShortUrl()).orElseThrow(NoSuchUrlFoundException::new);
        url.updateHit();
        return UrlServiceResponseDto.of(url);
    }
}
