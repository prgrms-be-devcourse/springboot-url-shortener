package com.prgrms.wonu606.shorturl.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.MethodSource;

class UrlLinkTest {

    @Nested
    class InitializeId {

        @ParameterizedTest
        @MethodSource("provideUrlLinkAndValidIdArguments")
        void whenCalledFirstTime_success(UrlLink urlLink, Long newId) {
            // When
            urlLink.initializeId(newId);

            // Then
            assertThat(urlLink.getId()).isEqualTo(newId);
        }

        @ParameterizedTest
        @MethodSource("provideUrlLinkAndValidIdArguments")
        void whenCalledTwice_throwException(UrlLink urlLink, Long newId) {
            // When
            urlLink.initializeId(newId);

            // When & Then
            assertThatThrownBy(() -> urlLink.initializeId(newId))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("ID가 이미 할당되어 있습니다. Current ID: " + newId);
        }

        @ParameterizedTest
        @MethodSource("provideUrlLinkAndInvalidIdArguments")
        void givenInvalidId_throwException(UrlLink urlLink, Long invalidId) {
            // When & Then
            assertThatThrownBy(() -> urlLink.initializeId(invalidId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("ID는 0보다 크고 null이 아니어야 합니다. Input ID: " + invalidId);
        }

        static Stream<Arguments> provideUrlLinkAndValidIdArguments() throws Exception {
            return createArgumentsWithUrlLinks(Stream.of(1L));
        }

        static Stream<Arguments> provideUrlLinkAndInvalidIdArguments() throws Exception {
            return createArgumentsWithUrlLinks(Stream.of(null, -1L, 0L));
        }

        static Stream<Arguments> createArgumentsWithUrlLinks(Stream<Long> ids) throws Exception {
            UrlLinkArgumentsProvider urlLinkArgumentsProvider = new UrlLinkArgumentsProvider();
            List<UrlLink> urlLinkList = urlLinkArgumentsProvider.provideArguments(null)
                    .map(arguments -> (UrlLink) arguments.get()[0])
                    .toList();

            List<Long> idList = ids.toList();

            List<Arguments> argumentsList = new ArrayList<>();
            for (int i = 0; i < Math.min(urlLinkList.size(), idList.size()); i++) {
                argumentsList.add(Arguments.of(urlLinkList.get(i), idList.get(i)));
            }
            return argumentsList.stream();
        }
    }

    static class UrlLinkArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(new UrlLink(
                            new Url("https://example.com"),
                            new HashedShortUrl("12345678")
                    )),
                    Arguments.of(new UrlLink(
                            new Url("https://www.example.com"),
                            new HashedShortUrl("123abcd")
                    )),
                    Arguments.of(new UrlLink(
                            new Url("https://www.example.com/index"),
                            new HashedShortUrl("ABC12ab")
                    ))
            );
        }
    }
}
