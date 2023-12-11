package com.programmers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateResponse {
    private String shortUri;
    private int count;
}
