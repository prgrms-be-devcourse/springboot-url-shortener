package org.daehwi.shorturl.util.encoder;

import static java.nio.charset.StandardCharsets.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Logger;

import org.daehwi.shorturl.controller.dto.ResponseStatus;
import org.daehwi.shorturl.exception.CustomException;

public interface Encoder {

	String encode(BigInteger bigInteger);

	static BigInteger generateSha256(String url, int hashSize) {
		Logger logger = Logger.getLogger(Encoder.class.getName());
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(url.getBytes(UTF_8));
			BigInteger bigInteger = new BigInteger(1, hash);
			// 16진수로 변환 후, hashSize 만큼만 잘라서 반환 (앞에서부터)
			String hashString = bigInteger.toString(16);
			String shortHash = hashString.substring(0, hashSize);
			return new BigInteger(shortHash, 16);
		} catch (Exception e) {
			logger.severe("An error occurred: " + e);
			throw new CustomException(ResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
