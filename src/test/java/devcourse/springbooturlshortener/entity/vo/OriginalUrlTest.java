package devcourse.springbooturlshortener.entity.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OriginalUrlTest {

    static Stream<Arguments> provideDeleteHttpPrefix() {
        return Stream.of(
                Arguments.of("http://www.example.com", "www.example.com"),
                Arguments.of("https://www.example.com", "www.example.com")
        );
    }

    static Stream<Arguments> provideTrimUrl() {
        return Stream.of(
                Arguments.of(" www.example.com", "www.example.com"),
                Arguments.of("www.example.com ", "www.example.com"),
                Arguments.of(" www.example.com     ", "www.example.com")
        );
    }

    @ParameterizedTest
    @MethodSource("provideDeleteHttpPrefix")
    @DisplayName("URL 생성 시 프로토콜이 있는 경우 프로토콜을 제거한다.")
    void createOriginalUrl(String inputUrl, String expectedUrl) {
        // When
        OriginalUrl originalUrl = new OriginalUrl(inputUrl);

        // Then
        assertThat(originalUrl.getValue()).isEqualTo(expectedUrl);
    }

    @ParameterizedTest
    @MethodSource("provideTrimUrl")
    @DisplayName("URL 생성 시 공백을 제거한다.")
    void createOriginalUrlWithoutProtocol(String inputUrl, String expectedUrl) {
        // When
        OriginalUrl originalUrl = new OriginalUrl(inputUrl);

        // Then
        assertThat(originalUrl.getValue()).isEqualTo(expectedUrl);
    }
}
