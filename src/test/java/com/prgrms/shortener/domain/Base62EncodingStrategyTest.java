package com.prgrms.shortener.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Base62EncodingStrategyTest {

  private final Base62EncodingStrategy strategy = new Base62EncodingStrategy();

  @ParameterizedTest
  @DisplayName("7글자 문자열(숫자,알파멧 대소문자)로 중복없이 인코딩 한다. 최대 숫자 2147483646")
  @CsvSource({
      "1,0000001",
      "62,0000010",
      "3844,0000100",
      "238328,0001000",
      "14776336,0010000",
      "14776335,000zzzz",
      "916132832,0100000",
      "2147483646,02LKcb0",

  })
  void test(int target, String encoded) {

    assertThat(strategy.encode(target)).isEqualTo(encoded);
  }

}
