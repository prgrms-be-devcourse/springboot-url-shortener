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
        if (this.id != null) {
            throw new IllegalStateException("ID가 이미 할당되어 있습니다. Current ID: " + newId);
        }
        this.id = newId;
    }

    public HashedShortUrl getHashedShortUrl() {
        return hashedShortUrl;
    }
}
