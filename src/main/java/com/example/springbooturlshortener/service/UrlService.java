package com.example.springbooturlshortener.service;

import static com.example.springbooturlshortener.exception.ErrorCode.INVALID_KEY;
import static com.example.springbooturlshortener.exception.ErrorCode.INVALID_URL;
import static com.example.springbooturlshortener.exception.ErrorCode.URL_NOT_FOUND;
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
    Url url;
    if ((url = findByOriginalUrl(originalUrl)) != null) {
      return url.getOriginalUrl();
    }

    url = new Url(originalUrl);
    Long id = urlRepository.save(new Url(originalUrl)).getId();
    String key = keyUtils.createKey(id);
    url.setUniqueKey(key);
    return url.shortenUrl();
  }

  public String findOriginalUrl(String key) {
    validateKey(key);
    Long id = keyUtils.decodeKey(key);
    Url url = urlRepository.findById(id).orElseThrow(() -> new CustomException(URL_NOT_FOUND));
    return url.getOriginalUrl();
  }

  private void validateUrl(String url) {
    if (url == null || url.isBlank()) {
      throw new CustomException(INVALID_URL);
    }
  }

  private void validateKey(String key) {
    if (key == null || key.isBlank()) {
      throw new CustomException(INVALID_KEY);
    }
  }

  private Url findByOriginalUrl(String originalUrl) {
    return urlRepository.findByOriginalUrl(originalUrl).orElse(null);
  }
}
