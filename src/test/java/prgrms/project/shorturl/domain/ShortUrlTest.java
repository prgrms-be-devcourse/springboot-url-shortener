package prgrms.project.shorturl.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShortUrlTest {

	@Test
	@DisplayName("numberOfRequests 필드의 값을 1 씩 증가시킨다.")
	void testIncreaseNumberOfRequests() {
	    //given
		var shortUrl = ShortUrl.createShortUrl("https://test.com", "abc123");
		var numberOfRequestsBeforeDoAction = shortUrl.getNumberOfRequests();

	    //when
		shortUrl.increaseNumberOfRequests();

	    //then
		assertThat(shortUrl.getNumberOfRequests()).isGreaterThan(numberOfRequestsBeforeDoAction);
	}
}
