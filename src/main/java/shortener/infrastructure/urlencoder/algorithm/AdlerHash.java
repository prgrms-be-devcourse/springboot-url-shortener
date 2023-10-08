package shortener.infrastructure.urlencoder.algorithm;

public class AdlerHash {

	private static final int MOD_ADLER = 65521;
	private static int dataSum = 1;
	private static int rollingSum = 0;

	public String encode(String originalUrl) {
		for (char urlElement : originalUrl.toCharArray()) {
			dataSum = (dataSum + urlElement) % MOD_ADLER;
			rollingSum = (rollingSum + dataSum) % MOD_ADLER;
		}
		int key = (rollingSum << 16) | dataSum;

		return Integer.toHexString(key);
	}
}
