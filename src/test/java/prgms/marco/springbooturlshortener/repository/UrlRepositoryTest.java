package prgms.marco.springbooturlshortener.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

    @Test
    @DisplayName("findByOriginUrl 쿼리 확인")
    void testFindByOriginUrl() {
        urlRepository.findByOriginUrl("www.originUrl.com");
    }
}