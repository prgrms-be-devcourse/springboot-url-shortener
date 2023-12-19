package com.dev.hello.user.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

	BUYER("ROLE_BUYER"),
	SELLER("ROLE_SELLER");

	private final String role;
}
