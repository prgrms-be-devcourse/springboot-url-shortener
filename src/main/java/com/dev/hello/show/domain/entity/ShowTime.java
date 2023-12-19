package com.dev.hello.show.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowTime {

	@Column(name = "show_total_minutes", nullable = false)
	private int totalMinutes;

	@Column(name = "show_interamission_minutes", nullable = false)
	private int intermissionMinutes;
}
