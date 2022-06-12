package shortUrl.shortUrl.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import shortUrl.shortUrl.domain.dto.CreateShortUrlDto;
import shortUrl.shortUrl.domain.dto.ShortUrlDto;
import shortUrl.shortUrl.domain.repository.UrlRepository;
import shortUrl.shortUrl.domain.service.UrlService;
import shortUrl.shortUrl.domain.value.Algorithm;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UrlControllerTest {

    @Autowired
    UrlController urlController;

    @Autowired
    UrlService urlService;

    @Autowired
    UrlRepository urlRepository;

    @BeforeEach
    void setup() {
        urlRepository.deleteAll();
    }

    @Nested
    class 단축Url_생성 {

        @Test
        @DisplayName("성공 : POST /create")
        public void Test() throws Exception {

            //given
            RestTemplate restTemplate = new RestTemplate();

            String longUrl = "https://www.naver.com/";

            CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto();
            createShortUrlDto.setOriginalUrl(longUrl);
            createShortUrlDto.setAlgorithm(Algorithm.BASE_56);

            //when
            String url = "http://localhost:8080/create";
            ShortUrlDto shortUrlDto = restTemplate.postForObject(url, createShortUrlDto, ShortUrlDto.class);
            String shortUrl = shortUrlDto.getShortUrl();

            //then
            String originalUrl = urlRepository.findByShortUrl(shortUrl)
                    .orElseThrow(() -> new RuntimeException()).getOriginalUrl();

            assertThat(originalUrl).isEqualTo(longUrl);
        }

        @Test
        @DisplayName("실패 : POST /create => 입력 url이 유효하지 않을 때")
        public void Test1() throws Exception {

            //given
            RestTemplate restTemplate = new RestTemplate();

            String longUrl = "https://nav/e.co";

            CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto();
            createShortUrlDto.setOriginalUrl(longUrl);
            createShortUrlDto.setAlgorithm(Algorithm.BASE_56);

            //when
            String url = "http://localhost:8080/create";

            //then
            assertThrows(RuntimeException.class, () -> restTemplate.postForObject(url, createShortUrlDto, ShortUrlDto.class));
        }
    }

    @Nested
    class 단축_url_바로가기 {

        @Test
        @DisplayName("성공 : POST /link")
        public void link() throws Exception {

            //given
            RestTemplate restTemplate = new RestTemplate();

            /**
             * short url 생성
             */
            String longUrl = "https://www.naver.com/";
            CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto(longUrl, Algorithm.BASE_56);
            String shortUrl = urlService.createShortUrl(createShortUrlDto).getShortUrl();

            //when
            String url = "http://localhost:8080/link";
            ShortUrlDto shortUrlDto = new ShortUrlDto();
            shortUrlDto.setShortUrl(shortUrl);
            ResponseEntity<String> response = restTemplate.postForEntity(url, shortUrlDto, String.class);

            //then
            HttpStatus statusCode = response.getStatusCode();
            assertThat(statusCode).isEqualTo(HttpStatus.FOUND);
        }

        @Test
        @DisplayName("실패 : POST /link -> 잘못된 short url")
        public void link_fail() throws Exception {

            //given
            RestTemplate restTemplate = new RestTemplate();

            /**
             * short url 생성
             */
            String longUrl = "https://www.naver.com/";
            CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto(longUrl, Algorithm.BASE_56);
            String shortUrl = urlService.createShortUrl(createShortUrlDto).getShortUrl();

            //when
            String wrongUrl = "wrongUrl";

            String url = "http://localhost:8080/link";
            ShortUrlDto shortUrlDto = new ShortUrlDto();
            shortUrlDto.setShortUrl(wrongUrl);

            //then
            assertThrows(RuntimeException.class, () -> restTemplate.postForEntity(url, shortUrlDto, String.class));
        }
    }

    @Nested
    class 주소창에_shortUrl_입력{
        @Test
        @DisplayName("성공 : POST /{shortUrl}")
        public void goShortUrl() throws Exception {

            //given
            RestTemplate restTemplate = new RestTemplate();

            /**
             * short url 생성
             */
            String longUrl = "https://www.naver.com/";
            CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto(longUrl, Algorithm.BASE_56);
            String shortUrl = urlService.createShortUrl(createShortUrlDto).getShortUrl();

            //when
            URI uri = UriComponentsBuilder
                    .fromUriString("http://localhost:8080")
                    .path("/{shortUrl}")
                    .encode()
                    .build()
                    .expand(shortUrl)
                    .toUri();
            ResponseEntity<String> response = restTemplate.postForEntity(uri, shortUrl, String.class);

            //then
            HttpStatus statusCode = response.getStatusCode();
            assertThat(statusCode).isEqualTo(HttpStatus.FOUND);
        }

        @Test
        @DisplayName("실패 : POST /{shortUrl} => 잘못된 short url")
        public void goShortUrl_fail() throws Exception {

            //given
            RestTemplate restTemplate = new RestTemplate();

            /**
             * short url 생성
             */
            String longUrl = "https://www.naver.com/";
            CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto(longUrl, Algorithm.BASE_56);
            String shortUrl = urlService.createShortUrl(createShortUrlDto).getShortUrl();

            //when
            String wrongUrl = "wrongUrl";

            URI uri = UriComponentsBuilder
                    .fromUriString("http://localhost:8080")
                    .path("/{shortUrl}")
                    .encode()
                    .build()
                    .expand(wrongUrl)
                    .toUri();

            //then
            assertThrows(RuntimeException.class, () -> restTemplate.postForEntity(uri, shortUrl, String.class));
        }
    }

    @Nested
    class shortUrl_정보조회{
        @Test
        @DisplayName("성공 : GET /{shortUrl}/info")
        public void Test() throws Exception {

            //given
            RestTemplate restTemplate = new RestTemplate();

            /**
             * short url 생성
             */
            String longUrl = "https://www.naver.com/";
            CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto(longUrl, Algorithm.BASE_56);
            String shortUrl = urlService.createShortUrl(createShortUrlDto).getShortUrl();

            //when
            URI uri = UriComponentsBuilder
                    .fromUriString("http://localhost:8080")
                    .path("/{shortUrl}/info")
                    .encode()
                    .build()
                    .expand(shortUrl)
                    .toUri();
            ShortUrlDto shortUrlDto = restTemplate.getForObject(uri, ShortUrlDto.class);

            //then
            assertThat(shortUrlDto.getOriginalUrl()).isEqualTo(longUrl);
            assertThat(shortUrlDto.getAlgorithm()).isEqualTo(Algorithm.BASE_56);
            assertThat(shortUrlDto.getHits()).isEqualTo(0L);
        }

        @Test
        @DisplayName("실패 : GET /{shortUrl}/info => 잘못된 url")
        public void getInfo_fail() throws Exception {

            //given
            RestTemplate restTemplate = new RestTemplate();

            /**
             * short url 생성
             */
            String longUrl = "https://www.naver.com/";
            CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto(longUrl, Algorithm.BASE_56);
            String shortUrl = urlService.createShortUrl(createShortUrlDto).getShortUrl();

            //when
            String wrongUrl = "wrongUrl";

            URI uri = UriComponentsBuilder
                    .fromUriString("http://localhost:8080")
                    .path("/{shortUrl}/info")
                    .encode()
                    .build()
                    .expand(wrongUrl)
                    .toUri();

            //then
            assertThrows(RuntimeException.class, () -> restTemplate.getForObject(uri, ShortUrlDto.class));
        }
    }
}