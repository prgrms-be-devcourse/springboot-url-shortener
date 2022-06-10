package com.sdardew.urlshortener.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "url")
@Table(indexes = @Index(columnList = "originalUrl"))
public class Url {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String originalUrl;

  private String shortUrl;

  private LocalDateTime createdAt;

  private Long requestCount;

  public Url() {
  }

  public Url(String originalUrl, LocalDateTime createdAt, Long requestCount) {
    this.originalUrl = originalUrl;
    this.createdAt = createdAt;
    this.requestCount = requestCount;
  }

  public Url(Long id, String originalUrl, String shortUrl, LocalDateTime createdAt, Long requestCount) {
    this.id = id;
    this.originalUrl = originalUrl;
    this.shortUrl = shortUrl;
    this.createdAt = createdAt;
    this.requestCount = requestCount;
  }

  public Long getId() {
    return id;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Long getRequestCount() {
    return requestCount;
  }

  public void setShortUrl(String shortUrl) {
    this.shortUrl = shortUrl;
  }
}
