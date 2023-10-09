package com.prgrms.wonu606.shorturl.service;

import com.prgrms.wonu606.shorturl.domain.Url;
import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.domain.UrlLink;
import java.util.Optional;

public interface UrlLinkRepository {

    Long save(UrlLink urlLink);

    boolean existByUrlHash(UrlHash urlHash);

    Optional<UrlLink> findByOriginal(Url originalUrl);
}
