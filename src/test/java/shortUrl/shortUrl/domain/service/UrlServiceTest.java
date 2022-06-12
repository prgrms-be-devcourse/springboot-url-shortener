package shortUrl.shortUrl.domain.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shortUrl.shortUrl.domain.dto.CreateShortUrlDto;
import shortUrl.shortUrl.domain.dto.ShortUrlDto;
import shortUrl.shortUrl.domain.entity.Url;
import shortUrl.shortUrl.domain.repository.UrlRepository;
import shortUrl.shortUrl.domain.value.Algorithm;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UrlServiceTest {

    @Autowired
    UrlService urlService;

    @Autowired
    UrlRepository urlRepository;

    @BeforeEach
    void setup() {
        String originalUrl = "https://www.naver.com/";
        base56UrlDto = new CreateShortUrlDto(originalUrl, Algorithm.BASE_56);
        sha256UrlDto = new CreateShortUrlDto(originalUrl, Algorithm.SHA_256);
    }

    @AfterEach
    void deleteAll() {
        urlRepository.deleteAll();
    }
    CreateShortUrlDto base56UrlDto;
    CreateShortUrlDto sha256UrlDto;

    @Nested
    class 생성_테스트 {

        @Test
        public void 성공() {

            //given
            String shortUrl = urlService.createShortUrl(base56UrlDto).getShortUrl();

            //when
            String originUrl = urlService.findOriginUrlByShortUrl(shortUrl);

            //then
            assertThat(originUrl).isEqualTo("https://www.naver.com/");
        }

        @Test
        @DisplayName("예외 발생 : 유효하지 않은 originalUrl 입력")
        public void 실패_잘못된_originalUrl_예외발생() {

            //given
            String originalUrl = "https://www.naver.cm/";
            CreateShortUrlDto wrongBase56UrlDto = new CreateShortUrlDto(originalUrl, Algorithm.BASE_56);

            //then
            assertThrows(RuntimeException.class, () -> urlService.createShortUrl(wrongBase56UrlDto));
        }

        @Test
        @DisplayName("성공 : 동일한 OriginalUrl - Algorithm로 생성한 ShortUrl이 존재하는 경우 기존의 것을 반환")
        public void 성공_이미존재하는_경우_기존의_shortUrl반환() {

            //given
            String shortUrl = urlService.createShortUrl(base56UrlDto).getShortUrl();

            //when
            String createSameUrl = urlService.createShortUrl(base56UrlDto).getShortUrl();

            //then
            assertThat(createSameUrl).isEqualTo(shortUrl);
        }

        @Test
        @DisplayName("성공 : 같은 originalUrl, 다른 Algorithm")
        public void Test() throws Exception {

            //given
            String base56Url = urlService.createShortUrl(base56UrlDto).getShortUrl();

            //when
            String sha256Url = urlService.createShortUrl(sha256UrlDto).getShortUrl();

            //then
            assertThat(base56Url).isNotEqualTo(sha256Url);
        }
    }

    @Test
    @Order(1)
    @DisplayName("예외 발생 : 동일한 shortUrl이 존재할 경우")
    public void 실패_동일한_shortUrl이_존재할_경우() throws Exception {

        //given
        String shortUrl = Algorithm.BASE_56.encoding(2L);
        Url url = new Url("https://www.naver.com/", Algorithm.SHA_256);
        url.saveShortUrl(shortUrl);

        //when
        urlRepository.save(url);

        //then
        assertThrows(RuntimeException.class, () -> urlService.createShortUrl(base56UrlDto));
    }

    @Nested
    class 기본조회 {

        @Test
        @DisplayName("첫 생성시 조회수 0, n번 조회할 경우 조회수 n 증가")
        public void 조회() throws Exception {

            //given
            String shortUrl = urlService.createShortUrl(base56UrlDto).getShortUrl();
            ShortUrlDto shortUrlDto = new ShortUrlDto();
            shortUrlDto.setShortUrl(shortUrl);

            Url url = urlRepository.findByShortUrl(shortUrl)
                    .orElseThrow(RuntimeException::new);

            assertThat(url.getHits()).isEqualTo(0L);

            //when
            int n = 10;
            for (int i = 0; i < n; i++) {
                urlService.findOriginUrlByShortUrl(shortUrl);
            }

            //then
            Url afterUrl = urlRepository.findByShortUrl(shortUrl)
                    .orElseThrow(RuntimeException::new);

            assertThat(afterUrl.getHits()).isEqualTo(n);
        }

        @Test
        @DisplayName("예외 발생 : shortUrl이 존재하지 않을 경우")
        public void notExistShortUrl() throws Exception {

            //given
            String shortUrl = urlService.createShortUrl(base56UrlDto).getShortUrl();

            //when
            String notExistShortUrl = "Not Exist Url";

            //then
            assertThrows(RuntimeException.class, () -> urlService.findOriginUrlByShortUrl(notExistShortUrl));
        }
    }

    @Nested
    class info조회 {

        @Test
        @DisplayName("성공 : getUrlInfo를 통해 정보를 가져옴")
        public void info조회() throws Exception {

            //given
            String shortUrl = urlService.createShortUrl(base56UrlDto).getShortUrl();

            int n = 10;
            for (int i = 0; i < n; i++) {
                urlService.findOriginUrlByShortUrl(shortUrl);
            }

            //when
            ShortUrlDto afterShortUrlDto = urlService.getUrlInfo(shortUrl);

            //then
            assertThat(afterShortUrlDto.getOriginalUrl()).isEqualTo(base56UrlDto.getOriginalUrl());
            assertThat(afterShortUrlDto.getShortUrl()).isEqualTo(shortUrl);
            assertThat(afterShortUrlDto.getHits()).isEqualTo(n);
            assertThat(afterShortUrlDto.getAlgorithm()).isEqualTo(base56UrlDto.getAlgorithm());
        }

        @Test
        @DisplayName("예외 발생 : shortUrl이 존재하지 않을 경우")
        public void info조회실패() throws Exception {

            //given
            String notExistShortUrl = "Not Exist Url";

            //then
            assertThrows(RuntimeException.class, () -> urlService.getUrlInfo(notExistShortUrl));
        }
    }
}
