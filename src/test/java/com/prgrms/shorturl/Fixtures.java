package com.prgrms.shorturl;

import com.prgrms.shorturl.url.model.ShortUrl;
import com.prgrms.shorturl.url.model.Urls;

public class Fixtures {

    public static ShortUrl oneLengthShortUrl() {
        return new ShortUrl("A");
    }

    public static Urls naverUrlInOriginUrl() {
        return new Urls("https://search.shopping.naver.com/search/category/100000015?NaPm=ct%3Dlnhoca02%7Cci%3Dshoppingwindow%7Ctr%3Dnsh%7Chk%3Df6aa7e43c22b8f359e4ac3efbaf097bbdfb9e7b7%7Ctrx%3D");
    }

}
