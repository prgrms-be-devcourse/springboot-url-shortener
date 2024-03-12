package com.programmers.urlshortener.algorithm;

public interface Algorithm<T, R> {
	R encode(T t);

	T decode(R r);
}
