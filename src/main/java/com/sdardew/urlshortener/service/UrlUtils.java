package com.sdardew.urlshortener.service;

import org.aspectj.weaver.ast.Call;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.concurrent.*;

@Component
public class UrlUtils {

  public boolean isExistingUrl(String originalUrl) throws IOException {
    if(checkFormat(originalUrl) && checkConnection(originalUrl)) {
      return true;
    }
    throw new MalformedURLException();
  }

  private boolean checkFormat(String originalUrl) {
    return originalUrl.matches("^(https?:\\/\\/)?([\\da-z\\.-]+\\.[a-z\\.]{2,6}|[\\d\\.]+)([\\/:?=&#]{1}[\\da-z\\.-]+)*[\\/\\?]?$");
  }

  private boolean checkConnection(String originalUrl) throws IOException {

    int responseCode;

    try {
      responseCode = getResponseCode(originalUrl);
    } catch (SocketTimeoutException e) {
      throw new UnknownHostException(originalUrl + " is unknown host");
    }

    if(HttpURLConnection.HTTP_OK != responseCode) {
      throw new UnknownHostException(originalUrl + " is unknown host");
    }

    return true;
  }

  public boolean exist(String loc) {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(loc).openConnection();
      connection.setRequestMethod("HEAD");
      return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    } catch (ProtocolException e) {
      return false;
    } catch (MalformedURLException e) {
      return false;
    } catch (IOException e) {
      return false;
    }
  }

  private int getResponseCode(String originalUrl) throws IOException {
    URL url = new URL(originalUrl);
    HttpURLConnection huc = (HttpURLConnection) url.openConnection();
    huc.setRequestMethod("HEAD");
    huc.setConnectTimeout(1_000);
    int responseCode = HttpURLConnection.HTTP_CLIENT_TIMEOUT;
    try {
      responseCode = huc.getResponseCode();
    } catch (SocketTimeoutException e) {
//      e.printStackTrace();
    } finally {
      huc.disconnect();
    }
    return responseCode;
  }
}
