package com.waterfogsw.springbooturlshortener.url.util;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

import com.waterfogsw.springbooturlshortener.url.entity.HashType;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class HashGenerator {

  private static final Logger log = LoggerFactory.getLogger(HashGenerator.class);

  public String generate(String str, HashType hashType, int hashLength) {
    Assert.isTrue(isNotEmpty(str), "String must be provided");
    Assert.notNull(hashType, "HashType must be provided");
    Assert.isTrue(hashLength > 0 && hashType.getMaxLength() >= hashLength,
        "Hash length out of range");

    String hashString;
    try {
      MessageDigest md = MessageDigest.getInstance(hashType.name());
      md.update(str.getBytes(StandardCharsets.UTF_8));
      byte[] byteData = md.digest();
      StringBuilder sb = new StringBuilder();
      for (byte byteDatum : byteData) {
        sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
      }
      hashString = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      log.warn(e.getMessage());
      throw new IllegalArgumentException(e.getMessage());
    }

    return hashString.substring(0, hashLength);
  }
}
