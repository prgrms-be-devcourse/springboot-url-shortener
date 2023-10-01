package com.prgrms.shorturl.domain.url.entity;

import com.prgrms.shorturl.algorithm.AlgorithmType;
import com.prgrms.shorturl.common.exception.BaseException;
import com.prgrms.shorturl.common.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UrlTest {

    @Test
    @DisplayName("[예외] 올바른 URL 형식이 아닐경우 예외를 발생시킨다.")
    void cannotBuildInvalidUrlTest() {
        //given
        String url = "httpa://tech.kakaopay.com/post/msa-transaction/";

        //when & then
        assertThatThrownBy(() -> Url.builder()
                .originUrl(url)
                .algorithmType(AlgorithmType.BASE62)
                .build()).isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_URL_FORMAT);
    }
}