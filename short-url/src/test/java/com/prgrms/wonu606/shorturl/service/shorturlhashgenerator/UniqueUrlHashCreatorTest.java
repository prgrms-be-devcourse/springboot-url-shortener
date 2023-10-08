package com.prgrms.wonu606.shorturl.service.shorturlhashgenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.prgrms.wonu606.shorturl.domain.Url;
import com.prgrms.wonu606.shorturl.domain.UrlHash;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UniqueUrlHashCreatorTest {

    @Autowired
    UniqueUrlHashCreator uniqueUrlHashCreator;

    @MockBean
    UrlHashExistenceChecker urlHashExistenceChecker;

    @Nested
    class CreateMethodTests {

        @Test
        void success_creatingUniqUrlHash() {
            // Given
            given(urlHashExistenceChecker.exists(any())).willReturn(false);
            Url url = new Url("https://example.com");

            // When
            UrlHash urlHash = uniqueUrlHashCreator.create(url);

            // Then
            assertThat(urlHash).isNotNull();
        }

        @Test
        void failure_throwException() {
            // Given
            given(urlHashExistenceChecker.exists(any())).willReturn(true);
            Url url = new Url("https://example.com");

            // When & Then
            assertThatThrownBy(() -> uniqueUrlHashCreator.create(url))
                    .isInstanceOf(UniqueHashCreationException.class)
                    .hasMessage("유니크한 URL 해시를 생성하는 데 실패했습니다.");
        }
    }
}
