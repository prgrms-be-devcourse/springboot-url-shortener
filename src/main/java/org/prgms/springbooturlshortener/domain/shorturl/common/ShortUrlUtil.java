package org.prgms.springbooturlshortener.domain.shorturl.common;

public class ShortUrlUtil {
    public static String deleteProtocol(String originalUrl) {
        for (String protocol: ShortUrlConstant.PROTOCOLS) {
            if (originalUrl.startsWith(protocol)) {
                originalUrl = originalUrl.replace(protocol, "");
            }
        }

        return originalUrl;
    }
}
