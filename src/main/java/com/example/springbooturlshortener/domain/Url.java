package com.example.springbooturlshortener.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@SequenceGenerator(
  name = "URL_SEQ_GEN",
  sequenceName = "URL_SEQ",
  initialValue = 1000000
)
@Entity
public class Url {
  private final String BASE_URL = "/api/v1/url/";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
                  generator = "URL_SEQ_GEN"
  )
  private Long id;

  private String originalUrl;

  private String uniqueKey;


  protected Url() {
  }

  public Url(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public void setUniqueKey(String uniqueKey) {
    this.uniqueKey = uniqueKey;
  }

  public String shortenUrl() {
    return BASE_URL + uniqueKey;
  }
}
