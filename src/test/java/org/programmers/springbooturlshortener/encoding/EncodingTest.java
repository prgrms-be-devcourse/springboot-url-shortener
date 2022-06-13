package org.programmers.springbooturlshortener.encoding;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.programmers.springbooturlshortener.encoding.Encoding.BASE62;

class EncodingTest {

    @ParameterizedTest
    @DisplayName("인코딩 작업이 끝나면 적절한 shortUrl로 변환되어야 함")
    @MethodSource("encodeTestParams")
    void encode(Long original, Encoding encoding, String result) {
        assertThat(encoding.encode(original)).isEqualTo(result);
    }

    private static Stream<Arguments> encodeTestParams() {
        return Stream.of(
                arguments(1L, BASE62, "localhost:8080/11"),
                arguments(7952117461L, BASE62, "localhost:8080/131HAg8"),
                arguments(56800235584L, BASE62, "localhost:8080/10000001"),
                arguments(3521614606207L, BASE62, "localhost:8080/1zzzzzzz")
        );
    }

    @ParameterizedTest
    @DisplayName("변환된 Url이 어떤 인코딩으로 작성되었는지 확인되어야함")
    @MethodSource("ofShortenUrlParams")
    void ofShortenUrl(String shortenUrl, Encoding result) {
        assertThat(Encoding.ofShortenUrl(shortenUrl)).isEqualTo(result);
    }

    private static Stream<Arguments> ofShortenUrlParams() {
        return Stream.of(
                arguments("11", BASE62),
                arguments("131HAg8", BASE62)
        );
    }

    @ParameterizedTest
    @DisplayName("잘못된 인코딩 코드를 가진 Url의 경우 예외가 발생해야 함")
    @MethodSource("ofShortenUrlNotFoundParams")
    void ofShortenUrlNotFound(String shortenUrl) {
        assertThrows(NoSuchEncodingException.class, () -> Encoding.ofShortenUrl(shortenUrl));
    }

    private static Stream<Arguments> ofShortenUrlNotFoundParams() {
        return Stream.of(
                arguments("Z1"),
                arguments("X31HAg8")
        );
    }

    @ParameterizedTest
    @DisplayName("인코딩을 바탕으로 단축 URL의 저장 Key를 찾아내야 함")
    @MethodSource("getOriginalUrlKeyParams")
    void getOriginalUrlKey(String shortenUrl, Encoding encoding, String result) {
        assertThat(encoding.getOriginalUrlKey(shortenUrl)).isEqualTo(result);
    }

    private static Stream<Arguments> getOriginalUrlKeyParams() {
        return Stream.of(
                arguments("11", BASE62, "1"),
                arguments("131HAg8", BASE62, "31HAg8")
        );
    }

    @ParameterizedTest
    @DisplayName("인코딩에 맞지 않거나, 빈 Url key가 나올 경우 예외 발생해야 함")
    @MethodSource("getOriginalUrlKeyIllegalParams")
    void getOriginalUrlKeyIllegal(String shortenUrl, Encoding encoding) {
        assertThrows(IllegalUrlException.class, () -> encoding.getOriginalUrlKey(shortenUrl));
    }

    private static Stream<Arguments> getOriginalUrlKeyIllegalParams() {
        return Stream.of(
                arguments("1가", BASE62),
                arguments("1@33", BASE62),
                arguments("1", BASE62)
        );
    }
}