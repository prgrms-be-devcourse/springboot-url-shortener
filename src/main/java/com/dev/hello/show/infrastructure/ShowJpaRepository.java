package com.dev.hello.show.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.hello.show.domain.entity.Show;
import com.dev.hello.show.domain.repository.ShowRepository;

public interface ShowJpaRepository extends JpaRepository<Show, Long>, ShowRepository {
}
