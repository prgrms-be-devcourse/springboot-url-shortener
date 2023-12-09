package com.dev.shortenerurl.url.infrastructure.idgenerator;

import org.springframework.stereotype.Component;

import com.dev.shortenerurl.url.domain.IdGenerator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaIdGenerator implements IdGenerator {

	private final IdEntityJpaRepository idEntityJpaRepository;

	@Override
	public Long get() {
		IdEntity idEntity = idEntityJpaRepository.save(new IdEntity());
		return idEntity.getId();
	}
}
