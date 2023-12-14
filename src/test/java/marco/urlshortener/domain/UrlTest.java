package marco.urlshortener.domain;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class UrlTest {
    static final String BLANK_URL_MESSAGE = "url은 공백일 수 없습니다.";
    static final String OVER_255_URL_MESSAGE = "url은 255자 이내여야 합니다.";
    static final String INVALID_URL_FORMAT_MESSAGE = "유효하지 않은 url 형식입니다.";

    @Nested
    @DisplayName("url 생성 테스트")
    class ConstructorTest {
        @DisplayName("인스턴스 생성에 성공한다.")
        @Test
        void success() {
            // given
            String longUrl = "https://www.naver.com";

            // when
            ThrowingCallable create = () -> new Url(longUrl);

            // then
            assertThatNoException().isThrownBy(create);
        }

        @DisplayName("url이 공백이면 예외가 발생한다.")
        @ParameterizedTest(name = "input -> {0}")
        @NullAndEmptySource
        void failWithBlankUrl(String blankLongUrl) {
            // when
            ThrowingCallable create = () -> new Url(blankLongUrl);

            // then
            assertThatIllegalArgumentException().isThrownBy(create)
                    .withMessage(BLANK_URL_MESSAGE);
        }

        @DisplayName("url이 255자를 초과하면 예외가 발생한다.")
        @Test
        void failWithOver255Url() {
            // given
            String tooLongUrl = "https://www." + "google".repeat(300) + ".com";

            // when
            ThrowingCallable create = () -> new Url(tooLongUrl);

            // then
            assertThatIllegalArgumentException().isThrownBy(create)
                    .withMessage(OVER_255_URL_MESSAGE);
        }

        @DisplayName("url의 형식이 유효하지 않으면 예외가 발생한다.")
        @Test
        void failWithInvalidFormatUrl() {
            // given
            String invalidLongUrl = "1234";

            // when
            ThrowingCallable create = () -> new Url(invalidLongUrl);

            // then
            assertThatIllegalArgumentException().isThrownBy(create)
                    .withMessage(INVALID_URL_FORMAT_MESSAGE);
        }
    }
}
