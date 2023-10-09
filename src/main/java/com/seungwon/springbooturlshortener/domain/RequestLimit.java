package com.seungwon.springbooturlshortener.domain;

import java.time.Duration;

import org.springframework.stereotype.Component;

import com.seungwon.springbooturlshortener.exception.ExcessiveRequestException;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

@Component
public class RequestLimit {
	private static final int CAPACITY = 10;
	private static final int CONSUME_BUCKET_COUNT = 1;
	private static final int DURATION = 1;
	private final Bucket bucket = Bucket.builder()
		.addLimit(Bandwidth.simple(CAPACITY, Duration.ofMinutes(DURATION)))
		.build();

	public void checkAvailability() {
		if (bucket.tryConsume(CONSUME_BUCKET_COUNT)) {
			return;
		}

		throw new ExcessiveRequestException();
	}
}
