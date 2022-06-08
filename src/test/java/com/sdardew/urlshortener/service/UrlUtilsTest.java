package com.sdardew.urlshortener.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UrlUtilsTest {

  static UrlUtils urlUtils;

  @BeforeAll
  static void beforeAll() {
    urlUtils = new UrlUtils();
  }

  @Test
  @DisplayName("url이 존재하는지 확인 가능하다")
  void testIsExistingUrl() {
    assertAll(
      () -> assertThat(urlUtils.isExistingUrl("https://www.google.com/")).isTrue(),
      () -> assertThrows(
        MalformedURLException.class,
        () -> urlUtils.isExistingUrl("www.google.com")),
      () -> assertThrows(
        UnknownHostException.class,
        () -> urlUtils.isExistingUrl("http://localhost.com"))
      );
  }

  @Test
  @DisplayName("display")
  void test() {


  }
}