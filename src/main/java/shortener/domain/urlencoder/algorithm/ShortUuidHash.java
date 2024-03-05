package shortener.domain.urlencoder.algorithm;

import java.util.UUID;

public class ShortUuidHash {

	public String encode() {
		return UUID.randomUUID()
			.toString()
			.substring(0, 7);
	}
}
