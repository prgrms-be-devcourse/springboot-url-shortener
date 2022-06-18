package com.example.springbooturlshortener.service;

import static com.example.springbooturlshortener.exception.ErrorCode.INVALID_URL;

import com.example.springbooturlshortener.domain.Url;
import com.example.springbooturlshortener.domain.UrlRepository;
import com.example.springbooturlshortener.exception.CustomException;
import com.example.springbooturlshortener.util.KeyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UrlService {
  private KeyUtils keyUtils;
  private UrlRepository urlRepository;

  public UrlService(KeyUtils uniqueKeyUtils,
    UrlRepository userRepository) {
    this.keyUtils = uniqueKeyUtils;
    this.urlRepository = userRepository;
  }

  public String shortenUrl(String originalUrl) {
    validateUrl(originalUrl);
    Url url = new Url(originalUrl);
    Long id = urlRepository.save(new Url(originalUrl));
    String key = keyUtils.createKey(id);
    url.setUniqueKey(key);
    return url.shortenUrl();
  }

  private void validateUrl(String url) {
    if (url == null || url.isBlank()) {
      throw new CustomException(INVALID_URL);
    }
  }

  public String findOriginalUrl(String key) {
    return null;
  }
}
