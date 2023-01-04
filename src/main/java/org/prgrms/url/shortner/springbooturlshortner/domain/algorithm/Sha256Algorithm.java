package org.prgrms.url.shortner.springbooturlshortner.domain.algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import org.prgrms.url.shortner.springbooturlshortner.global.error.exception.AlgorithmException;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sha256Algorithm implements ShortUrlAlgorithm {

	private static final Base64.Encoder urlEncoder = Base64.getUrlEncoder();
	private final static int LENGTH = 8;

	private byte[] encrypt(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new AlgorithmException();
		}

	}

	public String execute(String originUrl) {
		Random random = new Random();
		byte[] encrypt = encrypt(originUrl);
		String encode = urlEncoder.withoutPadding().encodeToString(encrypt);
		int start = random.nextInt(encrypt.length - LENGTH);
		return encode.substring(start, start + LENGTH);
	}

}
