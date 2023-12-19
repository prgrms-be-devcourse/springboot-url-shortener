package com.dev.hello.show.domain.entity.seat;

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
public class SeatPositionInfo {

	@Column(name = "seat_sector")
	private String sector;

	@Column(name = "seat_row", nullable = false)
	private String row;

	@Column(name = "seat_col", nullable = false)
	private int col;
}
