package com.prgrms.shortener.domain;

import static com.google.common.base.Preconditions.checkArgument;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "shortened-url")
public class ShortenedUrl {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column
  private String originalUrl;

  @Column
  private String key;

  public void assignOriginalUrl(String url) {
    this.originalUrl = url;
  }

  public void assignKey(String key) {
    checkArgument(key.length() == 8, "key는 8글자로 구성되어야 합니다.");
    this.key = key;
  }

  // 알고리즘을 enum 형태로 보관환 뒤에 entity에서 호출할 수 있도록 할까?
}
