package devcourse.springbooturlshortener.urlalgorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Base62EncodeUrlAlgorithmTest {

    @ParameterizedTest
    @CsvSource({
            "123456789",
            "987654321",
            "218340105584895"
    })
    @DisplayName("URL 인코딩이 제대로 이루어진다.")
    void urlEncoder(Long id) {
        // Given
        Base62EncodeUrlAlgorithm encoder = new Base62EncodeUrlAlgorithm();

        // When
        String encodedUrl = encoder.urlEncoder(id);

        // Then
        assertThat(encodedUrl).isNotBlank();
    }

    @Test
    @DisplayName("최대값을 초과하는 ID를 인코딩하면 예외가 발생한다.")
    void urlEncoderMaxValueExceeded() {
        // Given
        Base62EncodeUrlAlgorithm encoder = new Base62EncodeUrlAlgorithm();
        Long id = Base62EncodeUrlAlgorithm.MAX_VALUE + 1;

        // When, Then
        assertThatThrownBy(() -> encoder.urlEncoder(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최대값을 초과했습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "123456789",
            "987654321",
            "218340105584895"
    })
    @DisplayName("URL 디코딩이 제대로 이루어진다.")
    void urlDecoder(Long id) {
        // Given
        Base62EncodeUrlAlgorithm encoder = new Base62EncodeUrlAlgorithm();
        String encodedUrl = encoder.urlEncoder(id);

        // When
        Long decodedId = encoder.urlDecoder(encodedUrl);

        // Then
        assertThat(decodedId).isEqualTo(id);
    }

    @ParameterizedTest
    @CsvSource({
            "123456789",
            "987654321",
            "218340105584895"
    })
    @DisplayName("8자리를 초과하는 문자열을 디코딩하면 예외가 발생한다.")
    void urlDecoderInvalidFormat(String invalidEncodedUrl) {
        // Given
        Base62EncodeUrlAlgorithm encoder = new Base62EncodeUrlAlgorithm();

        // When, Then
        assertThatThrownBy(() -> encoder.urlDecoder(invalidEncodedUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최대 길이를 초과했습니다.");
    }
}