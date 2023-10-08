package com.prgrms.wonu606.shorturl.domain;

public class UrlLink {

    private Long id;
    private final Url originalUrl;
    private final HashedShortUrl hashedShortUrl;

    public UrlLink(Url originalUrl, HashedShortUrl hashedShortUrl) {
        this.originalUrl = originalUrl;
        this.hashedShortUrl = hashedShortUrl;
    }

    public void initializeId(Long newId) {
        if (newId == null || newId <= 0) {
            throw new IllegalArgumentException("ID는 0보다 크고 null이 아니어야 합니다. Input ID: " + newId);
        }
        if (this.id != null) {
            throw new IllegalStateException("ID가 이미 할당되어 있습니다. Current ID: " + newId);
        }
        this.id = newId;
    }

    public HashedShortUrl getHashedShortUrl() {
        return hashedShortUrl;
    }

    public long getId() {
        return id;
    }
}
