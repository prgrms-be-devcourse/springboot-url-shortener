package com.dev.shortenerurl.url.infrastructure.idgenerator;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dev.shortenerurl.url.domain.IdGenerator;

@DisplayName("[JpaIdGenerator 테스트]")
@DataJpaTest
class JpaIdGeneratorTest {

	private final IdGenerator idGenerator;

	public JpaIdGeneratorTest(@Autowired IdEntityJpaRepository idEntityJpaRepository) {
		this.idGenerator = new JpaIdGenerator(idEntityJpaRepository);
	}

	@Test
	@DisplayName("[유니크한 ID 를 생성한다]")
	void get() throws InterruptedException {
		//given
		//생성된 Id 가 담기는 리스트
		Set<Long> ids = new HashSet<>();

		int count = 3;
		ExecutorService executorService = Executors.newFixedThreadPool(count);
		CountDownLatch latch = new CountDownLatch(count);

		//when
		for (int i = 0; i < count; i++) {
			executorService.execute(() -> {
				ids.add(idGenerator.get());
				latch.countDown();
			});
		}

		latch.await();

		//then
		assertThat(ids).hasSize(count);
	}
}