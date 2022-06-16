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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(unique = true, length = 1000)
  private String originalUrl;
  @Column(length = 7, unique = true)
  private String shortenedKey;
  @Column
  private long count;

  public ShortenedUrl(Integer id) {
    this.id = id;
  }

  public void assignOriginalUrl(String url) {
    checkArgument(url.length() <= 1000, "url의 최대 길이는 1000입니다.");
    this.originalUrl = url;
  }

  public void assignKey(String key) {
    checkArgument(key.length() == 7, "key는 7개의 문자로 구성되어야 합니다.");
    this.shortenedKey = key;
  }

  public long getCount() {
    return count;
  }
}
