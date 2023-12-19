package com.dev.shortenerurl.url.domain.encoder;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Base62Encoder 테스트]")
class Base62EncoderTest {

	private final Encoder encoder = new Base62Encoder();

	@Test
	@DisplayName("long 정수를 BASE_62 를 통해 인코딩한다")
	void encode() {
		//given
		Long id = 125L;

		//when
		String encodedString = encoder.encode(id);

		//then
		assertThat(encodedString)
			.hasSize(7)
			.isEqualTo("BCAAAAA");
	}
}