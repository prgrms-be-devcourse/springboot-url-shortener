package com.example.shorturl.domain;

import static com.example.shorturl.exception.ErrorCode.*;

import com.example.shorturl.exception.CustomException;

public enum Algorithm {
    BASE_62,
    SHA_256;

    public static void isValidAlgorithm(String value) {
        try {
            Algorithm.valueOf(value);
        } catch (IllegalArgumentException e) {
			throw new CustomException(ALGORITHM_NOT_SUPPORTED);
		}
    }
}
