package com.sdardew.urlshortener.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "url")
public class Url {

  @Id
  private String shortUrl;

  private String redirectUrl;

  private LocalDateTime createdAt;

  private Long requestCount;

  public Url() {
  }

  public Url(String shortUrl, String redirectUrl, LocalDateTime createdAt, Long requestCount) {
    this.shortUrl = shortUrl;
    this.redirectUrl = redirectUrl;
    this.createdAt = createdAt;
    this.requestCount = requestCount;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Long getRequestCount() {
    return requestCount;
  }
}
