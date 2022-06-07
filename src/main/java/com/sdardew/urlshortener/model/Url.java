package com.sdardew.urlshortener.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "url")
public class Url {

  @Id
  String id;

  String redirectUrl;

  LocalDateTime createdAt;

  Long requestCount;
}
