package org.prgrms.urlshortener.fixture.entity;

import org.prgrms.urlshortener.domain.Algorithm;
import org.prgrms.urlshortener.domain.Url;

public class UrlBuilder {

    public static Url createOne(String originUrl) {
        return new Url(originUrl, Algorithm.BASE_62);
    }

}
