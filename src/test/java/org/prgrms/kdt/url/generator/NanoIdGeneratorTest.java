package org.prgrms.kdt.url.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.url.ShortUrlRepository;

@ExtendWith(MockitoExtension.class)
class NanoIdGeneratorTest {

  @InjectMocks
  NanoIdGenerator nanoIdGenerator;

  @Mock
  ShortUrlRepository shortUrlRepository;

  @Test
  @DisplayName("nanoid 방식으로 ID를 생성한다.")
  void testGenerate() {
    given(shortUrlRepository.findById(any())).willReturn(Optional.empty());

    String id = nanoIdGenerator.generate();

    assertThat(id).hasSize(8);
  }

}