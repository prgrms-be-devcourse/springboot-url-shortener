package com.dev.shortenerurl.url.infrastructure.idgenerator;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IdEntityJpaRepository extends JpaRepository<IdEntity, Long> {
}
