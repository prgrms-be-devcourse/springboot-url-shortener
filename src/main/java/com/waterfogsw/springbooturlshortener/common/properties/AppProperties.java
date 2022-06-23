package com.waterfogsw.springbooturlshortener.common.properties;

import javax.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Validated
@ConstructorBinding
@ConfigurationProperties("app")
public class AppProperties {

  @NotEmpty
  private final String url;

  public String getUrl() {
    return url;
  }

  public AppProperties(String url) {
    this.url = url;
  }
}
