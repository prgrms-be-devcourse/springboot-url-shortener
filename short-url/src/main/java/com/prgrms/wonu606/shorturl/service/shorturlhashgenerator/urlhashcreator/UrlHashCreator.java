package com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.urlhashcreator;

import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.domain.Url;

public interface UrlHashCreator {

    UrlHash create(Url url, ChunkWeight weight);
}
