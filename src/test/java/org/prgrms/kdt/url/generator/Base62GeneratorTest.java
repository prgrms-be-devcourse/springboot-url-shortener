package org.prgrms.kdt.url.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
class Base62GeneratorTest {

  @InjectMocks
  Base62Generator base62Generator;

  @Mock
  ValueOperations<String, String> valueOps;

  @ParameterizedTest
  @CsvSource({"1, 1", "50000, d0s", "4912031, kBQj"})
  @DisplayName("base62 방식으로 ID를 생성한다.")
  void testGenerate(long incrementedId, String expected) {
    given(valueOps.increment(any())).willReturn(incrementedId);

    String generate = base62Generator.generate();

    assertThat(generate).isEqualTo(expected);
  }
}