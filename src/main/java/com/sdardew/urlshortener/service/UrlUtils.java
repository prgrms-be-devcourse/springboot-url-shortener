package com.sdardew.urlshortener.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;

@Component
public class UrlUtils {

  public void checkUrl(String originalUrl) throws IOException {
    if(!checkFormat(originalUrl)) {
      throw new MalformedURLException();
    }
    if (!checkExistingUrl(originalUrl)) {
      throw new UnknownHostException();
    }
  }

  private boolean checkFormat(String originalUrl) {
    return originalUrl.matches("^(https?:\\/\\/)?([\\da-z\\.-]+\\.[a-z\\.]{2,6}|[\\d\\.]+)([\\/:?=&#]{1}[\\da-z\\.-]+)*[\\/\\?]?$");
  }

  private boolean checkExistingUrl(String originalUrl) throws IOException {
    URL url = new URL(originalUrl);
    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
    huc.setRequestMethod("HEAD");
    huc.setConnectTimeout(1_000);
    int responseCode;
    try {
      responseCode = huc.getResponseCode();
    } catch (SocketTimeoutException e) {
      responseCode = HttpURLConnection.HTTP_CLIENT_TIMEOUT;
    } finally {
      huc.disconnect();
    }
    return responseCode == HttpURLConnection.HTTP_OK;
  }
}
