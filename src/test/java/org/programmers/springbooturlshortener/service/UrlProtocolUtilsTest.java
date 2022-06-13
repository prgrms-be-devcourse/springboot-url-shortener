package org.programmers.springbooturlshortener.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class UrlProtocolUtilsTest {

    @ParameterizedTest
    @DisplayName("url에 프로토콜이 없을 경우 기본 프로토콜: HTTP를 추가함")
    @MethodSource("addHttpParam")
    void addHttp(String url, String urlAfterAddedProtocol) {
        assertThat(UrlProtocolUtils.addingHttpIfNoProtocolIn(url)).isEqualTo(urlAfterAddedProtocol);
    }

    private static Stream<Arguments> addHttpParam() {
        return Stream.of(
                arguments("naver.com", "http://naver.com"),
                arguments("foo.bar", "http://foo.bar")
        );
    }

    @ParameterizedTest
    @DisplayName("url에 프로토콜이 있을 경우 그대로 반환함")
    @MethodSource("addNoProtocolParam")
    void addNoProtocol(String url) {
        assertThat(UrlProtocolUtils.addingHttpIfNoProtocolIn(url)).isEqualTo(url);
    }

    private static Stream<Arguments> addNoProtocolParam() {
        return Stream.of(
                arguments("http://naver.com"),
                arguments("https://foo.bar"),
                arguments("ftp://foo.bar"),
                arguments("telnet://foo.bar"),
                arguments("news://foo.bar"),
                arguments("mms://foo.bar")
        );
    }
}