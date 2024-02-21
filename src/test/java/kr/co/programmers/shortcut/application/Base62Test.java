package kr.co.programmers.shortcut.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Base62Test {

	private Base62 base62;

	@BeforeEach
	void setUp() {
		base62 = new Base62();
	}

	@Test
	void testEncodeNumber() {
		long number = 12345L;
		String expectedEncoded = "7D3";

		String encoded = base62.encodeNumber(number);

		assertEquals(expectedEncoded, encoded);
	}

	@Test
	void testDecodeKey() {
		String encodedKey = "3d7";
		long expectedNumber = 29329;

		long decoded = base62.decodeKey(encodedKey);

		assertEquals(expectedNumber, decoded);
	}

	@Test
	void testEncodingAndDecoding() {
		long originalNumber = 123456789L;

		// 인코딩 후 디코딩
		String encoded = base62.encodeNumber(originalNumber);
		long decoded = base62.decodeKey(encoded);

		assertEquals(originalNumber, decoded, "디코딩된 값이 원래 숫자와 일치 하지 않습니다.");
	}
}
