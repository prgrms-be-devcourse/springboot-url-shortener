package com.prgrms.wonu606.shorturl.service.shorturlhashgenerator;

import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.service.UrlLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlHashExistenceChecker {

    private final UrlLinkRepository urlLinkRepository;

    public boolean exists(UrlHash urlHash) {
        return urlLinkRepository.existByUrlHash(urlHash);
    }
}
