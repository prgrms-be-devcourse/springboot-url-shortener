package com.prgrms.shortener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.shortener.entity.Url;
import com.prgrms.shortener.entity.UrlRepository;
import com.prgrms.shortener.exception.ErrorMessage;
import com.prgrms.shortener.exception.custom.NotFoundException;
import com.prgrms.shortener.util.EncoderUtil;

@Service
public class UrlService {

  private final String APP_URL;
  private final UrlRepository urlRepository;

  private final EncoderUtil encoderUtil;

  public UrlService(@Value("${app.url}") String baseUrl,
      UrlRepository urlRepository,
      EncoderUtil encoderUtil
  ) {

    this.APP_URL = baseUrl;
    this.urlRepository = urlRepository;
    this.encoderUtil = encoderUtil;
  }

  @Transactional
  public String createShortUrl(String originalUrl) {

    if (originalUrl == null) {
      throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_URL.getMessage());
    }

    Url existUrl = urlRepository.findByOriginalUrl(originalUrl).orElse(null);

    if (existUrl != null) {
      return APP_URL + existUrl.getUniqueKey();
    }

    Url url = Url.builder()
        .originalUrl(originalUrl)
        .build();
    urlRepository.save(url);
    String uniqueKey = encoderUtil.encoding(url.getId());
    url.saveUniqueKey(uniqueKey);

    return APP_URL + uniqueKey;
  }

  @Transactional
  public String findByUniqueKey(String uniqueKey) {

    if (uniqueKey == null) {
      throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT_KEY.getMessage());
    }

    Url url = urlRepository.findByUniqueKey(uniqueKey).
        orElseThrow(() -> new NotFoundException(ErrorMessage.URL_NOT_FOUND.getMessage()));

    return url.getOriginalUrl();
  }
}
