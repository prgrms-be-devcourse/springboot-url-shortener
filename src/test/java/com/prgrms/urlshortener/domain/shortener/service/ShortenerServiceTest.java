package com.prgrms.urlshortener.domain.shortener.service;

import com.prgrms.urlshortener.domain.shortener.domain.UrlRepository;
import com.prgrms.urlshortener.global.error.exception.WrongFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.sql.DataSourceDefinition;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ShortenerServiceTest {
    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private ShortenerService shortenerService;

    @Nested
    class GetShortenUrlTest {
        @Nested
        class GetShortenUrlValidationTest {
            @ParameterizedTest(name = "{1}")
            @MethodSource("blankUrlParameter")
            void testValidationFailCausedByBlankUrl(String url, String testName) {
                WrongFieldException exception = assertThrows(WrongFieldException.class, () -> shortenerService.getShortenUrl(url));
                assertThat(exception.getReason()).isEqualTo("blank is not allowed at url");
            }

            @Test
            @DisplayName("전달된 url이 옳바르지 않은 경우 예외가 잘 발생하는지 확인하기 위한 테스트")
            void testValidationFailCauseByUrl() {
                WrongFieldException exception = assertThrows(WrongFieldException.class, () -> shortenerService.getShortenUrl("abc.afafadf"));
                assertThat(exception.getReason()).isEqualTo("not a valid url");
            }

            private static Stream<Arguments> blankUrlParameter() {
                return Stream.of(
                        Arguments.of("", "\"\"인 경우 예외가 잘 발생하는지 확인하기 위한 테스트"),
                        Arguments.of(" ", "\" \"인 경우 예외가 잘 발생하는지 확인하기 위한 테스트"),
                        Arguments.of(null, "null인 경우 예외가 잘 발생하는지 확인하기 위한 테스트")
                );
            }
        }
    }

}