package com.taehan.urlshortener.model;

import com.taehan.urlshortener.util.Converter;

import java.util.function.Function;

public enum AlgorithmType {
    BASE62(Converter::base62);

    private final Function<Long, String> job;

    AlgorithmType(Function<Long, String> job) {
        this.job = job;
    }

    public String convert(long value) {
        return this.job.apply(value);
    }
}
