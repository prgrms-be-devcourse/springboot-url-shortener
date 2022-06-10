package com.prgrms.shortener.domain;

import static com.google.common.base.Preconditions.checkArgument;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ShortenedUrl {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  @Column(unique = true)
  private String originalUrl;
  @Column(length = 7, unique = true)
  private String shortenedKey;

  public ShortenedUrl(Integer id) {
    this.id = id;
  }

  public void assignOriginalUrl(String url) {
    this.originalUrl = url;
  }

  public void assignKey(String key) {
    checkArgument(key.length() == 7, "key는 7개의 문자로 구성되어야 합니다.");
    this.shortenedKey = key;
  }

}
