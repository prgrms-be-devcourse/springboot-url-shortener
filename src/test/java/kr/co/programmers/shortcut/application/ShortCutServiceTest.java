package kr.co.programmers.shortcut.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import kr.co.programmers.shortcut.domain.ShortCut;
import kr.co.programmers.shortcut.domain.ShortCutRepository;
import kr.co.programmers.shortcut.dto.ShortCutCreateRequest;
import kr.co.programmers.shortcut.dto.ShortCutResponse;

@SpringBootTest
class ShortCutServiceTest {
	@Autowired
	private ShortCutService shortCutService;

	@MockBean
	private ShortCutRepository shortCutRepository;

	@MockBean
	private Base62 base62;

	@MockBean
	private ShortCutGenerator shortCutGenerator;

	@Test
	public void testCreateShortCut() {
		// 준비
		ShortCutCreateRequest request = new ShortCutCreateRequest("http://original-url.com");
		ShortCut shortCut = new ShortCut(request.originalURL());
		ShortCut savedShortCut = new ShortCut(request.originalURL());
		savedShortCut.setId(1L); // 예제 ID
		String encodedId = "encodedString";
		String expectedShortCutURL = "http://short.url/encodedString";

		when(shortCutRepository.save(any(ShortCut.class))).thenReturn(savedShortCut);
		when(base62.encodeNumber(savedShortCut.getId())).thenReturn(encodedId);
		when(shortCutGenerator.generateShortCutURL(encodedId)).thenReturn(expectedShortCutURL);

		// 실행
		ShortCutResponse response = shortCutService.createShortCut(request);

		// 검증
		assertNotNull(response);
		assertEquals(expectedShortCutURL, response.newURL());

		// 확인
		verify(shortCutRepository, times(2)).save(any(ShortCut.class)); // 저장 메소드가 두 번 호출되었는지 확인
		verify(base62).encodeNumber(savedShortCut.getId());
		verify(shortCutGenerator).generateShortCutURL(encodedId);
	}
}