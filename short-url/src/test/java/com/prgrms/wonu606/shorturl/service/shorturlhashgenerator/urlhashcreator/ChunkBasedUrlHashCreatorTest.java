package com.prgrms.wonu606.shorturl.service.shorturlhashgenerator.urlhashcreator;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.wonu606.shorturl.domain.Url;
import com.prgrms.wonu606.shorturl.domain.UrlHash;
import io.micrometer.common.util.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChunkBasedUrlHashCreatorTest {

    private ChunkBasedUrlHashCreator chunkBasedUrlHashCreator;

    @BeforeEach
    void setup() {
        chunkBasedUrlHashCreator = new ChunkBasedUrlHashCreator();
    }

    @Test
    void create_thenReturnNonBlank() {
        // Given
        Url url = new Url("https://x.com");
        ChunkWeight chunkWeight = new ChunkWeight(1);

        // When
        UrlHash urlHash = chunkBasedUrlHashCreator.create(url, chunkWeight);

        // Then
        assertThat(StringUtils.isNotBlank(urlHash.value())).isTrue();
    }
}
