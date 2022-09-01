package prgrms.project.shorturl.global.algorithm;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component(value = "base62")
public class Base62Generator implements ShortUrlGenerator {

	private static final Random RANDOM = new Random();
	private static final int IDX = 62;
	private static final char[] BASE62_CHARS =
		"abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	@Override
	public String generate() {
		RANDOM.setSeed(System.currentTimeMillis());
		var key = RANDOM.nextLong(Long.MAX_VALUE);
		var stringBuilder = new StringBuilder();

		while (key > 0) {
			stringBuilder.append(BASE62_CHARS[(int)(key % IDX)]);
			key /= IDX;
		}

		return stringBuilder.reverse().toString();
	}
}
