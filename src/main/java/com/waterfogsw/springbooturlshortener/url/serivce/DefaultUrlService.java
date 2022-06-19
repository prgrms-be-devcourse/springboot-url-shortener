package com.waterfogsw.springbooturlshortener.url.serivce;

import static org.hibernate.internal.util.StringHelper.isNotEmpty;

import com.waterfogsw.springbooturlshortener.common.exception.NotFoundException;
import com.waterfogsw.springbooturlshortener.common.properties.AppProperties;
import com.waterfogsw.springbooturlshortener.url.entity.HashType;
import com.waterfogsw.springbooturlshortener.url.entity.Url;
import com.waterfogsw.springbooturlshortener.url.repository.UrlRepository;
import com.waterfogsw.springbooturlshortener.url.util.HashGenerator;
import com.waterfogsw.springbooturlshortener.url.util.UrlBase64Encoder;
import java.text.MessageFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class DefaultUrlService implements UrlService {

  private static final int MAX_HASH_LENGTH = 5;

  private final AppProperties appProperties;
  private final HashGenerator hashGenerator;
  private final UrlBase64Encoder urlBase64Encoder;
  private final UrlRepository urlRepository;

  public DefaultUrlService(
      AppProperties appProperties,
      HashGenerator hashGenerator,
      UrlBase64Encoder urlBase64Encoder,
      UrlRepository urlRepository
  ) {
    this.appProperties = appProperties;
    this.hashGenerator = hashGenerator;
    this.urlBase64Encoder = urlBase64Encoder;
    this.urlRepository = urlRepository;
  }

  @Override
  @Transactional
  public String shorten(String orgUrl, HashType hashType) {
    Assert.isTrue(isNotEmpty(orgUrl), "Original url must be provided");
    Assert.notNull(hashType, "Hash type must be provided");

    final var hash = hashGenerator.generate(orgUrl, hashType, MAX_HASH_LENGTH);

    final var url = Url.builder()
        .orgUrl(orgUrl)
        .hashType(hashType)
        .hash(hash)
        .build();

    final var savedUrl = urlRepository.save(url);
    final var encodedValue = urlBase64Encoder.encode(savedUrl.getHash());

    return MessageFormat.format("{0}/{1}", appProperties.getUrl(), encodedValue);
  }

  @Override
  public String getRedirectUrl(String shortKey) {
    Assert.isTrue(isNotEmpty(shortKey), "Short key must be provided");
    final var hash = urlBase64Encoder.decode(shortKey);

    return urlRepository.findByHash(hash)
        .orElseThrow(NotFoundException::new)
        .getOrgUrl();
  }
}
