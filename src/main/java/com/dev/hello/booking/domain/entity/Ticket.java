package com.dev.hello.booking.domain.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import com.dev.hello.common.entity.TimeBaseEntity;
import com.dev.hello.show.domain.entity.seat.Seat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ticket_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ticket_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "ticket_booking_id", nullable = false)
	private Booking booking;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "ticket_seat_id", nullable = false)
	private Seat seat;
}
