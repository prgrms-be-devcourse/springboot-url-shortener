package kr.co.programmers.shortcut.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.programmers.shortcut.domain.ShortCut;
import kr.co.programmers.shortcut.domain.ShortCutRepository;
import kr.co.programmers.shortcut.dto.ShortCutCreateRequest;
import kr.co.programmers.shortcut.dto.ShortCutResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShortCutService {

	private final ShortCutRepository shortCutRepository;

	private final ShortCutGenerator shortCutGenerator;

	private final Base62 base62;

	@Transactional
	public ShortCutResponse createShortCut(ShortCutCreateRequest shortCutCreateRequest) {
		String originalURL = shortCutCreateRequest.originalURL();
		ShortCut shortCut = new ShortCut(originalURL);

		ShortCut savedShortCut = shortCutRepository.save(shortCut);
		Long savedShortCutId = savedShortCut.getId();

		String encodeId = base62.encodeNumber(savedShortCutId);

		savedShortCut.updateEncodedId(encodeId);
		shortCutRepository.save(savedShortCut);

		String shortCutURL = shortCutGenerator.generateShortCutURL(encodeId);

		return ShortCutResponse.from(savedShortCut, shortCutURL);
	}

	public String getOriginalURL(String encodedId) {
		Long decodedId = base62.decodeKey(encodedId);
		ShortCut findedShortCut = shortCutRepository.getReferenceById(decodedId);
		return findedShortCut.getOriginalURL();
	}
}
