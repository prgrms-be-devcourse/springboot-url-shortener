package com.prgrms.wonu606.shorturl.repository;

import com.prgrms.wonu606.shorturl.domain.UrlHash;
import com.prgrms.wonu606.shorturl.domain.UrlLink;
import com.prgrms.wonu606.shorturl.service.UrlLinkRepository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class LocalUrlLinkRepository implements UrlLinkRepository {

    private final Map<Long, UrlLink> storage = new ConcurrentHashMap<>();
    private final Map<UrlHash, UrlLink> hashedUrlIndex = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Long save(UrlLink urlLink) {
        validateUrlLink(urlLink);

        Long newId = generateNewId();

        urlLink.initializeId(newId);

        storage.put(newId, urlLink);

        hashedUrlIndex.compute(urlLink.getUrlHash(), (key, existingUrlLink) -> {
            if (existingUrlLink != null) {
                throw new IllegalArgumentException("UrlHash가 이미 인덱싱 되어 있습니다. Current UrlHash: " + urlLink.getUrlHash());
            }
            return urlLink;
        });

        return newId;
    }

    @Override
    public boolean existByUrlHash(UrlHash urlHash) {
        return hashedUrlIndex.containsKey(urlHash);
    }

    private long generateNewId() {
        return idGenerator.getAndIncrement();
    }

    private void validateUrlLink(UrlLink urlLink) {
        if (urlLink == null) {
            throw new IllegalArgumentException("UrlLink가 null일 경우 저장할 수 없습니다.");
        }
        if (urlLink.getUrlHash() == null || urlLink.getOriginalUrl() == null) {
            throw new IllegalArgumentException("UrlLink의 필드는 null일 경우 저장할 수 없습니다.");
        }
    }
}
