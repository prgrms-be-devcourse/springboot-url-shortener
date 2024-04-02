package com.young.shortenerurl.support;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Import({AsyncTestConfig.class})
public class IntegrationTest {
}
