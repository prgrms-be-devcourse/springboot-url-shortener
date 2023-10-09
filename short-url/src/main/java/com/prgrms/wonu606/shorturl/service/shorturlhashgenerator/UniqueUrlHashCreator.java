package com.prgrms.wonu606.shorturl.service.shorturlhashgenerator;

import com.prgrms.wonu606.shorturl.domain.Url;
import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.urlhashcreator.ChunkWeight;
import com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.urlhashcreator.UrlHashCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueUrlHashCreator {

    private static final int MAX_GENERATION_ATTEMPTS = 100;

    private final UrlHashCreator urlHashCreator;
    private final UrlHashExistenceChecker urlHashExistenceChecker;

    public UrlHash create(Url originalUrl) {
        for (int attemptCount = 1; attemptCount <= MAX_GENERATION_ATTEMPTS; attemptCount++) {
            UrlHash urlHash = urlHashCreator.create(originalUrl, new ChunkWeight(attemptCount));
            if (!urlHashExistenceChecker.exists(urlHash)) {
                return urlHash;
            }
        }

        throw new UniqueHashCreationException("유니크한 URL 해시를 생성하는 데 실패했습니다.");
    }
}
