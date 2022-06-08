package com.taehan.urlshortener.model;

import java.util.function.Function;

public enum AlgorithmType {
    BASE62(url  -> {return "base62";}),
    CUSTOM(url  -> {return url + " custom";});

    private final Function<String, String> job;

    AlgorithmType(Function<String, String> job) {
        this.job = job;
    }

    public String convert(String url) {
        return this.job.apply(url);
    }


}
