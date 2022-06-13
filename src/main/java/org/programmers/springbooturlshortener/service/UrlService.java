package org.programmers.springbooturlshortener.service;

import org.programmers.springbooturlshortener.encoding.Encoding;
import org.springframework.transaction.annotation.Transactional;

public interface UrlService {
    @Transactional
    String newUrl(String original, Encoding encoding);

    String getOriginalUrl(String shortenUrl);
}
