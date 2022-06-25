package com.waterfogsw.springbooturlshortener.url.util;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UrlBase64Encoder {

  public String encode(String src) {
    Assert.isTrue(isNotEmpty(src), "String must be provided");
    return Base64.getUrlEncoder().encodeToString(src.getBytes());
  }

  public String decode(String src) {
    Assert.isTrue(isNotEmpty(src), "String must be provided");

    final var bytes = Base64.getUrlDecoder().decode(src);
    return new String(bytes, StandardCharsets.UTF_8);
  }

}
