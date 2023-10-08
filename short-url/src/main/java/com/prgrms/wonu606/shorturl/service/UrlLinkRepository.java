package com.prgrms.wonu606.shorturl.service;

import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.domain.UrlLink;

public interface UrlLinkRepository {

    Long save(UrlLink urlLink);

    boolean existByUrlHash(UrlHash urlHash);
}
