package com.dev.hello.booking.domain.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import com.dev.hello.common.entity.TimeBaseEntity;
import com.dev.hello.show.domain.entity.Show;
import com.dev.hello.user.domain.entity.User;

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
@Table(name = "booking_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booking extends TimeBaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "booking_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "booking_user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "booking_show_id", nullable = false)
	private Show show;
}
