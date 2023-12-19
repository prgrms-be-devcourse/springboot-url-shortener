package com.dev.hello.show.domain.entity.seat;

import java.time.LocalTime;

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
public class ShowRound {

	@Column(name = "seat_show_round")
	private int round;

	@Column(name = "seat_start_time")
	private LocalTime startTime;
}
