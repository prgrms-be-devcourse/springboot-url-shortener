package com.sdardew.urlshortener.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

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
      () -> assertDoesNotThrow(
        () -> urlUtils.checkUrl("https://www.google.com/")),
      () -> assertThrows(
        MalformedURLException.class,
        () -> urlUtils.checkUrl("www.google.com")),
      () -> assertThrows(
        UnknownHostException.class,
        () -> urlUtils.checkUrl("http://localhost.com"))
      );
  }
}