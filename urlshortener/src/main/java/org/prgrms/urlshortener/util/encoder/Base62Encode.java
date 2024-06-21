package org.prgrms.urlshortener.util.encoder;

import org.prgrms.urlshortener.domain.Url;
import org.prgrms.urlshortener.respository.UrlRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Base62Encode implements EncodePolicy {

	private static final String baseCharacters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final char[] baseCharacterList = baseCharacters.toCharArray();

	private static final int BASE = baseCharacterList.length;

	private final UrlRepository urlRepository;

	@Override
	public String encode(String originUrl) {
		Url savedUrl = urlRepository.findByOriginUrl(originUrl)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 originUrl입니다."));
		String encodedUrl = encodeWithId(savedUrl.getId().intValue());

		return encodedUrl;
	}

	private String encodeWithId(int id) {
		StringBuilder stringBuilder = new StringBuilder();

		while(id > 0) {
			int index = id % BASE - 1;
			stringBuilder.append(baseCharacterList[index]);
			id /= BASE;
		}

		String encodedUrl = stringBuilder.toString();
		return encodedUrl;
	}

}
