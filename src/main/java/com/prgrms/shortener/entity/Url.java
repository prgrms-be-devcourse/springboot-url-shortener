package com.prgrms.shortener.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotBlank;

@Entity
@TableGenerator(
    name = "URL_SEQ_GEN",
    initialValue = 10000000,
    allocationSize = 1
)
public class Url extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "URL_SEQ_GEN")
  private Long id;

  @NotBlank
  private String originalUrl;

  private String uniqueKey;

  protected Url() {

  }

  private Url(Builder builder) {

    this.originalUrl = builder.originalUrl;
  }

  public void saveUniqueKey(String uniqueKey) {

    if (uniqueKey == null) {
      throw new IllegalArgumentException();
    }

    this.uniqueKey = uniqueKey;
  }

  public static class Builder {

    private String originalUrl;

    public Builder originalUrl(String originalUrl) {

      this.originalUrl = originalUrl;
      return this;
    }

    public Url build() {

      return new Url(this);
    }
  }

  public static Builder builder() {

    return new Builder();
  }

  public Long getId() {

    return id;
  }

  public String getOriginalUrl() {

    return originalUrl;
  }

  public String getUniqueKey() {

    return uniqueKey;
  }
}
