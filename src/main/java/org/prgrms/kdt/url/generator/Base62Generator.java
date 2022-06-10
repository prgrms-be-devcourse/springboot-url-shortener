package org.prgrms.kdt.url.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class Base62Generator implements IdGenerator {

  private final ValueOperations<String, String> valueOps;

  @Value("generator.key")
  private String key;

  public Base62Generator(ValueOperations<String, String> valueOps) {
    this.valueOps = valueOps;
  }

  @Override
  public String generate() {
    StringBuilder shortUrl = new StringBuilder();
    Long incrementedId = valueOps.increment(key);

    if (incrementedId == null) {
      throw new IllegalArgumentException();
    }

    int id = incrementedId.intValue();
    while (id > 0) {
      shortUrl.append(DEFAULT_CHARACTER_SET[id % 62]);
      id = id / 62;
    }
    return shortUrl.reverse().toString();
  }
}