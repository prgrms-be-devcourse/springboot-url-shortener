package com.dev.hello.show.domain.entity.seat;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

import java.time.LocalDate;

import com.dev.hello.common.entity.TimeBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "seat_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "seat_id")
	private Long id;

	@Enumerated(STRING)
	@Column(name = "seat_grade", nullable = false)
	private SeatGrade seatGrade;

	@Column(name = "seat_is_seat", nullable = false)
	private boolean isSeat;

	@Embedded
	private SeatPositionInfo positionInfo;

	@Column(name = "seat_price", nullable = false)
	private int price;

	@Column(name = "seat_show_date", nullable = false)
	private LocalDate showDate;

	@Embedded
	private ShowRound showRound;

	@Column(name = "seat_is_booked", nullable = false)
	private boolean isBooked;
}
