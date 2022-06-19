package com.waterfogsw.springbooturlshortener.common.properties;

import javax.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("app")
public class AppProperties {

  @NotEmpty
  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
