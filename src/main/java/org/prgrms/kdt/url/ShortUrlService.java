package org.prgrms.kdt.url;

import java.util.Map;
import org.prgrms.kdt.url.generator.IdGenerator;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlService {

  private final ShortUrlRepository shortUrlRepository;
  private final Map<String, IdGenerator> urlGenerators;

  public ShortUrlService(
      ShortUrlRepository shortUrlRepository,
      Map<String, IdGenerator> urlGenerators) {
    this.shortUrlRepository = shortUrlRepository;
    this.urlGenerators = urlGenerators;
  }

  public ShortUrl shortenUrl(String url, String type) {
    String id = urlGenerators.get(type).generate();
    ShortUrl shortUrl = new ShortUrl(id, url, 0);
    return shortUrlRepository.save(shortUrl);
  }

  public ShortUrl getShortUrl(String id) {
    return shortUrlRepository.findById(id).orElseThrow();
  }

  public String getOriginalUrl(String id) {
    ShortUrl increasedCountUrl = shortUrlRepository.findById(id)
        .map(ShortUrl::increasedCountUrl)
        .orElseThrow();
    shortUrlRepository.save(increasedCountUrl);
    return increasedCountUrl.originalUrl();
  }

}