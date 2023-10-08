package com.prgrms.wonu606.shorturl.service;

import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.domain.UrlLink;

public interface ShortenUrlRepository {

    Long save(UrlLink urlLink);

    boolean existByShortenedUrl(UrlHash urlHash);
}
