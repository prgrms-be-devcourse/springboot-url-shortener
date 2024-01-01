package org.prgms.springbooturlshortener.domain.shorturl.common;

import java.util.Arrays;

public class ShortUrlUtil {
    public static String deleteProtocol(String originalUrl) {
        return Arrays.stream(ShortUrlConstant.PROTOCOLS).reduce(originalUrl, (url, protocol) -> url.replace(protocol, ""));
    }
}
