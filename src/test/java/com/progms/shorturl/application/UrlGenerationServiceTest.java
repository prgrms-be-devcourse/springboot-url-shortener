package com.progms.shorturl.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class UrlGenerationServiceTest {

    UrlGenerationService urlGenerationService = new UrlGenerationService();

    @DisplayName("URL Base62 파싱 성공")
    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "61, z",
            "35, Z"
    })
    void urlParsing_success(Long id, String result) {
        // given, when
        String predict = urlGenerationService.parseBase62(id);

        // then
        Assertions.assertThat(predict).isEqualTo(result);
    }
}
