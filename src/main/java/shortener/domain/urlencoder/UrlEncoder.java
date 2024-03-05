package shortener.domain.urlencoder;

import shortener.domain.ShortUrl;
import shortener.domain.urlencoder.algorithm.AdlerHash;
import shortener.global.error.ErrorCode;
import shortener.global.error.exception.BusinessException;
import shortener.domain.urlencoder.algorithm.AlgorithmType;
import shortener.domain.urlencoder.algorithm.Base62Hash;
import shortener.domain.urlencoder.algorithm.ShortUuidHash;

public class UrlEncoder {

	private static final Base62Hash base62Hash = new Base62Hash();
	private static final ShortUuidHash shortUuidHash = new ShortUuidHash();
	private static final AdlerHash adlerHash = new AdlerHash();

	public static String getShortUrl(ShortUrl shortUrl, int algorithmId) {
		if (AlgorithmType.BASE62.getId() == algorithmId) {
			Long id = shortUrl.getId();

			return base62Hash.encode(id);
		} else if (AlgorithmType.SHORT_UUID.getId() == algorithmId) {
			return shortUuidHash.encode();
		} else if (AlgorithmType.ADLER.getId() == algorithmId) {
			String originalUrl = shortUrl.getOriginalUrl();

			return adlerHash.encode(originalUrl);
		} else {
			throw new BusinessException(ErrorCode.INVALID_ENCODING_ALGORITHM);
		}
	}
}
