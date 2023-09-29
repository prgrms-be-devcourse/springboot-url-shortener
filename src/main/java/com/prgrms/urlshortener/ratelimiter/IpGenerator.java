package com.prgrms.urlshortener.ratelimiter;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IpGenerator {

    private static final List<String> IP_HEADERS = Arrays.asList(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP"
    );

    public static String getClientIP(HttpServletRequest request) {
        Optional<String> validIp = IP_HEADERS.stream()
                .map(request::getHeader)
                .filter(IpGenerator::isValidIp)
                .findFirst();

        return validIp.orElseGet(request::getRemoteAddr);
    }

    private static boolean isValidIp(String ip) {
        return ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip);
    }
}