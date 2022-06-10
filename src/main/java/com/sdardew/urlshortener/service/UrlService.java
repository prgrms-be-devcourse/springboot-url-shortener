package com.sdardew.urlshortener.service;

import com.sdardew.urlshortener.dto.CreateUrlRequestDto;
import com.sdardew.urlshortener.dto.CreateUrlResponseDto;
import com.sdardew.urlshortener.model.Url;
import com.sdardew.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {
  private final UrlRepository urlRepository;
  private final ShortUrlConverter shortUrlConverter;
  private final UrlUtils urlUtils;

  public UrlService(UrlRepository urlRepository, ShortUrlConverter shortUrlConverter, UrlUtils urlUtils) {
    this.urlRepository = urlRepository;
    this.shortUrlConverter = shortUrlConverter;
    this.urlUtils = urlUtils;
  }

  public String getOriginalUrl(String shortUrl) {
    Optional<Url> found = urlRepository.findUrlByShortUrl(shortUrl);
    if(found.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 short url입니다.");
    }
    return found.get().getOriginalUrl();
  }

  @Transactional
  public CreateUrlResponseDto createUrl(CreateUrlRequestDto requestDto) throws IOException {
    // 1. check the url validation
    urlUtils.checkUrl(requestDto.getUrl());

    // create new shortURl
    Optional<Url> urlByOriginalUrl = urlRepository.findUrlByOriginalUrl(requestDto.getUrl());

    if(urlByOriginalUrl.isPresent()) {
      return new CreateUrlResponseDto(urlByOriginalUrl.get().getShortUrl());
    }

    Url url = new Url(requestDto.getUrl(), LocalDateTime.now(), 0L);
    urlRepository.save(url); // id를 생성
    url.setShortUrl(shortUrlConverter.encode(url.getId()));

    return new CreateUrlResponseDto(url.getShortUrl());
  }

}
