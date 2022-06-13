package org.programmers.springbooturlshortener.service;

import org.programmers.springbooturlshortener.encoding.Encoding;

public interface UrlService {

    String newUrl(String original, Encoding encoding);

    String getOriginalUrl(String shortenUrl);

    long getCalledData(String shortenUrl);
}
