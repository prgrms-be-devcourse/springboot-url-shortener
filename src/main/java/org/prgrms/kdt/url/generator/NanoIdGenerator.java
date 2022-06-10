package org.prgrms.kdt.url.generator;

import java.security.SecureRandom;
import org.prgrms.kdt.url.ShortUrlRepository;
import org.springframework.stereotype.Component;

@Component
public class NanoIdGenerator implements IdGenerator {

  private static final SecureRandom DEFAULT_RANDOM_GENERATOR = new SecureRandom();

  private static final int DEFAULT_SIZE = 8;

  private final ShortUrlRepository shortUrlRepository;

  public NanoIdGenerator(ShortUrlRepository shortUrlRepository) {
    this.shortUrlRepository = shortUrlRepository;
  }

  @Override
  public String generate() {
    while (true) {
      String nanoId = randomNanoId();

      if (shortUrlRepository.findById(nanoId).isPresent()) {
        continue;
      }

      return nanoId;
    }
  }

  private String randomNanoId() {
    final int mask = (2 << (int) Math.floor(Math.log(DEFAULT_CHARACTER_SET.length - 1) / Math.log(2))) - 1;
    final int step = (int) Math.ceil(1.6 * mask * DEFAULT_SIZE / DEFAULT_CHARACTER_SET.length);

    final StringBuilder idBuilder = new StringBuilder();

    while (true) {

      final byte[] bytes = new byte[step];
      DEFAULT_RANDOM_GENERATOR.nextBytes(bytes);

      for (int i = 0; i < step; i++) {

        final int characterSetIndex = bytes[i] & mask;

        if (characterSetIndex < DEFAULT_CHARACTER_SET.length) {
          idBuilder.append(DEFAULT_CHARACTER_SET[characterSetIndex]);
          if (idBuilder.length() == DEFAULT_SIZE) {
            return idBuilder.toString();
          }
        }
      }
    }
  }
}