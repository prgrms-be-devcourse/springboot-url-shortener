package com.waterfogsw.springbooturlshortener.url.entity;

public enum HashType {
  MD5(32),
  SHA256(64);

  private final int maxLength;

  HashType(int maxLength) {
    this.maxLength = maxLength;
  }

  public int getMaxLength() {
    return maxLength;
  }
}
