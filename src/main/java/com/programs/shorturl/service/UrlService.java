package com.programs.shorturl.service;

import com.programs.shorturl.domain.Url;
import com.programs.shorturl.dto.UrlRequestDto;
import com.programs.shorturl.dto.UrlResponseDto;
import com.programs.shorturl.exception.NotExistException;
import com.programs.shorturl.repository.UrlRepository;
import com.programs.shorturl.util.Base58;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UrlService {

  private final UrlRepository urlRepository;
  private final Base58 base58;

  public UrlService(UrlRepository urlRepository, Base58 base58) {
    this.urlRepository = urlRepository;
    this.base58 = base58;
  }

  public UrlResponseDto createShortUrl(UrlRequestDto urlRequestDto) {
    String originalUrl;
    if (!urlRequestDto.getOriginalUrl().startsWith("http")) {
      originalUrl = "https://" + urlRequestDto.getOriginalUrl();
    }else{
      originalUrl = urlRequestDto.getOriginalUrl();
    }

    String shortUrl = base58.generateShortUrl();
    Url temp = urlRepository.findByOriginalUrl(originalUrl).orElseGet(
        () -> urlRepository.save(
            Url.builder()
                .originalUrl(originalUrl)
                .shortUrl(shortUrl)
                .build()
        )
    );

    return new UrlResponseDto(temp.getOriginalUrl(),  temp.getShortUrl());
  }

  public String getOriginalUrl(String shortUrl) {
    Url url = urlRepository.findByShortUrl(shortUrl).orElseThrow(NotExistException::new);
    return url.getOriginalUrl();
  }
}
