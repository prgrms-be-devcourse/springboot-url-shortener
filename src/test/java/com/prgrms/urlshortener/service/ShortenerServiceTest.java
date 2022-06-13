package com.prgrms.urlshortener.service;

import com.prgrms.urlshortener.common.error.exception.NonExistedUrlException;
import com.prgrms.urlshortener.domain.url.Url;
import com.prgrms.urlshortener.domain.url.UrlRepository;
import com.prgrms.urlshortener.common.error.exception.WrongFieldException;
import com.prgrms.urlshortener.service.ShortenerService;
import org.assertj.core.internal.bytebuddy.asm.Advice;
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

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShortenerServiceTest {
    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private ShortenerService shortenerService;

    private Url url = new Url("abc.com", 0);

    @DisplayName("단축 url 생성 테스트")
    @Nested
    class GetShortenUrlTest {
        @DisplayName("단축 url 생성을 위해 전달된 값을 제대로 검증하는지 테스트")
        @Nested
        class GetShortenUrlValidationTest {
            @DisplayName("전달된 url이 \"\", \" \", null인 경우 예외가 잘 발생하는지 테스트")
            @ParameterizedTest(name = "[{index}] {1}")
            @MethodSource("blankUrlParameter")
            void testValidationFailCausedByBlankUrl(String url, String testName) {
                WrongFieldException exception = assertThrows(WrongFieldException.class, () -> shortenerService.createShortenUrl(url));
                assertThat(exception.getReason()).isEqualTo("blank is not allowed at url");
            }

            @DisplayName("전달된 url 패턴 이 옳바르지 않은 경우 예외가 잘 발생하는지 확인하기 위한 테스트")
            @Test
            void testValidationFailCauseByUrl() {
                WrongFieldException exception = assertThrows(WrongFieldException.class, () -> shortenerService.createShortenUrl("abc.afafadf"));
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

        @DisplayName("중복 저장 허용시키지 않는지 테스트")
        @Test
        void testDuplicateUrl() {
            when(urlRepository.findByUrl("abc.com")).thenReturn(Optional.of(url));

            shortenerService.createShortenUrl("abc.com");

            verify(urlRepository, never()).save(any());
        }

        @DisplayName("url을 저장하는 메서드가 잘 호출되는지 테스트")
        @Test
        void testSavingUrl() {
            when(urlRepository.findByUrl("abc.com")).thenReturn(Optional.empty());

            shortenerService.createShortenUrl("abc.com");

            verify(urlRepository).save(any());
        }
    }

    @DisplayName("original url 가져오기 테스트")
    @Nested
    class GetOriginalUrlTest {
        @DisplayName("전달된 encodedId가 옳바르지 않은 경우 예외를 잘 발생하는지 테스트")
        @ParameterizedTest(name = "[{index}] {0}")
        @MethodSource("encodedIdParameter")
        void testValidationFailCausedByUrl(String encodeId) {
            assertThrows(WrongFieldException.class, () -> shortenerService.getOriginalUrl(encodeId));
        }

        private static Stream<Arguments> encodedIdParameter() {
            return Stream.of(
                    Arguments.of("adfa=-"),
                    Arguments.of("_Ou7Jm"),
                    Arguments.of("_-+")
            );
        }
    }
}