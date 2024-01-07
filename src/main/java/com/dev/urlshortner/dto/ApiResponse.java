package com.dev.urlshortner.dto;

public record ApiResponse<T>(String code, String message, T result) {
	public static <T> ApiResponse<T> ok(T result) {
		return new ApiResponse<>("200", "ok", result);
	}
}
