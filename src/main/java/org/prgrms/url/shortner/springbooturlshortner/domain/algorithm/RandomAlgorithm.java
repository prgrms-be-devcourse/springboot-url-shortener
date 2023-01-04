package org.prgrms.url.shortner.springbooturlshortner.domain.algorithm;

import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomAlgorithm implements ShortUrlAlgorithm {

	private final static int LENGTH = 8;
	private static final char[] Base64URL = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
	};

	@Override
	public String execute(String originUrl) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < LENGTH; i++) {
			sb.append(Base64URL[random.nextInt(Base64URL.length)]);
		}

		return sb.toString();
	}

}
