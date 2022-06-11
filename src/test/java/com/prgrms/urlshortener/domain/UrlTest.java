package com.prgrms.urlshortener.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UrlTest {

    @DisplayName("올바른 URL이라면 Url 객체를 생성한다.")
    @ParameterizedTest(name = "URL = {0}")
    @ValueSource(strings = {
        "https://studyhardd.tistory.com/76",
        "http://www.studyhardd.tistory.com/76",
        "studyhardd.tistory.com/76"
    })
    void generate_Url(String originUrl) {
        // given
        // when
        Url url = new Url(originUrl);

        // then
        assertThat(url).isNotNull();
    }

    @DisplayName("올바르지 않은 URL로 Url 객체를 생성할 수 없다.")
    @ParameterizedTest(name = "URL = {0}")
    @ValueSource(strings = {
        "htt://studyhardd.tistory.com/76",
        "httpa://studyhardd.tistory.com/76",
        "https://studyhardd.tistory.c/76",
        "https://....wwww.studyhardd.tistory.c/76",
        "//studyhardd.tistory.com/76",
        "https://stud%yhardd.tistory.com/76",
    })
    void generate_Url_with_wrong_URL(String url) {
        assertThatThrownBy(() -> new Url(url))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("단축 Url을 할당할 수 있다.")
    @Test
    void assign_shortedUrl() {
        // given
        Url url = new Url("https://studyhardd.tistory.com/76");
        ShortedUrl shortedUrl = new ShortedUrl("1234567");

        // when
        url.assignShortedUrl(shortedUrl);

        // then
        assertThat(url.getShortedUrl()).isEqualTo(shortedUrl);
    }

    @DisplayName("할당된 단축 Url을 변경할 수 없다.")
    @Test
    void assign_already_assigned() {
        // given
        Url url = new Url("https://studyhardd.tistory.com/76");
        url.assignShortedUrl(new ShortedUrl("1234567"));
        ShortedUrl shortedUrl = new ShortedUrl("12345");

        // when
        // then
        assertThatThrownBy(() -> url.assignShortedUrl(shortedUrl))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
