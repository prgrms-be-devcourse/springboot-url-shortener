package com.waterfogsw.springbooturlshortener.url.entity;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

import com.waterfogsw.springbooturlshortener.common.entity.BaseTime;
import java.net.URL;
import java.net.URLConnection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.util.Assert;

@Entity
@Table(name = "urls")
public class Url extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(min = 1, max = 2048)
  private String orgUrl;

  @NotBlank
  @Size(min = 1, max = 2048)
  private String hash;

  @NotNull
  @Enumerated(EnumType.STRING)
  private HashType hashType;

  @NotNull
  @Min(0)
  private long requestCount;

  protected Url() {/*no-op*/}

  private Url(Builder builder) {
    Assert.notNull(builder, "Builder must be provided");
    Assert.isTrue(isNotEmpty(builder.orgUrl), "Origin url must be provided");
    Assert.isTrue(isValidUrl(builder.orgUrl), "Origin url is not valid");
    Assert.notNull(builder.hashType, "Hash type must be provided");

    this.orgUrl = builder.orgUrl;
    this.hash = builder.hash;
    this.hashType = builder.hashType;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getOrgUrl() {
    return orgUrl;
  }

  public String getHash() {
    return hash;
  }

  private boolean isValidUrl(String url) {
    try {
      URL connectionUrl = new URL(url);
      URLConnection conn = connectionUrl.openConnection();
      conn.connect();
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public static class Builder {

    private String orgUrl;
    private String hash;
    private HashType hashType;

    private Builder() {/*no-op*/}

    public Builder orgUrl(String orgUrl) {
      this.orgUrl = orgUrl;
      return this;
    }

    public Builder hash(String hash) {
      this.hash = hash;
      return this;
    }

    public Builder hashType(HashType hashType) {
      this.hashType = hashType;
      return this;
    }

    public Url build() {
      return new Url(this);
    }
  }
}
