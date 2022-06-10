package org.prgrms.kdt.url;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "ShortUrl", timeToLive = 60 * 60 * 24 * 7)
public record ShortUrl(
    @Id
    String shortUrl,
    String originalUrl,
    int viewCount
) {

  public ShortUrl increasedCountUrl() {
    return new ShortUrl(shortUrl, originalUrl, viewCount + 1);
  }
}